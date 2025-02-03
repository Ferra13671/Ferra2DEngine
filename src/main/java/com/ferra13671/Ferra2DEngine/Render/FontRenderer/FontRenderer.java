package com.ferra13671.Ferra2DEngine.Render.FontRenderer;

import com.ferra13671.Ferra2DEngine.Render.RenderHelper;
import com.ferra13671.Ferra2DEngine.Utils.ColorUtils;
import com.ferra13671.Ferra2DEngine.Utils.Generate.StringGenerator;
import com.ferra13671.TextureUtils.TextureStorage;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.lwjgl.opengl.GL11.glDisable;

public class FontRenderer implements Closeable {
    private static final int DEFAULT_PAGE_SIZE = 256;
    private static final int DEFAULT_PADDING_BETWEEN_CHARS = 5;

    private static final ExecutorService ASYNC_WORKER = Executors.newCachedThreadPool();
    private final HashMap<String, ArrayList<DrawEntry>> GLYPH_PAGE_CACHE = new HashMap<>();
    private final float originalSize;
    private final ArrayList<GlyphMap> maps = new ArrayList<>();
    private final HashMap<Character, Glyph> allGlyphs = new HashMap<>();
    private final int pageSize;
    private final int paddingBetweenChars;
    private final String prebakeGlyphs;
    private final int scaleMul = 2;
    private Font font;
    private Future<Void> prebakeGlyphsFuture;
    private boolean initialized;

    public FontRenderer(Font font, float sizePx, int charactersPerPage, int paddingBetweenChars, String prebakeCharacters) {
        this.originalSize = sizePx;
        this.pageSize = charactersPerPage;
        this.paddingBetweenChars = paddingBetweenChars;
        this.prebakeGlyphs = prebakeCharacters;
        init(font, sizePx);
    }

    public FontRenderer(Font font, float sizePx) {
        this(font, sizePx / 2, DEFAULT_PAGE_SIZE, DEFAULT_PADDING_BETWEEN_CHARS, null);
    }

    private int floorNearestMulN(int x) {
        return pageSize * (int) Math.floor((double) x / (double) pageSize);
    }

    public static String stripControlCodes(String text) {
        char[] chars = text.toCharArray();
        StringBuilder f = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            f.append(c);
        }
        return f.toString();
    }

    private void init(Font font, float sizePx) {
        if (initialized) throw new IllegalStateException("Double call to init()");
        initialized = true;
        this.font = font.deriveFont(sizePx * scaleMul);
        if (prebakeGlyphs != null && !prebakeGlyphs.isEmpty()) {
            prebakeGlyphsFuture = prebake();
        }
    }

    private Future<Void> prebake() {
        return ASYNC_WORKER.submit(() -> {
            for (char c : prebakeGlyphs.toCharArray()) {
                if (Thread.interrupted()) break;
                locateGlyph(c);
            }
            return null;
        });
    }

    private GlyphMap generateMap(char from, char to) {
        GlyphMap gm = new GlyphMap(from, to, font, StringGenerator.generateNextString(32, false, false, false), paddingBetweenChars);
        maps.add(gm);
        return gm;
    }

    private Glyph locateGlyph(char glyph) {
        return allGlyphs.computeIfAbsent(glyph, this::locateGlyphInternal);
    }

    private Glyph locateGlyphInternal(char glyph) {
        for (GlyphMap map : maps) {
            if (map.contains(glyph)) {
                return map.getGlyph(glyph);
            }
        }
        int base = floorNearestMulN(glyph);
        GlyphMap glyphMap = generateMap((char) base, (char) (base + pageSize));
        return glyphMap.getGlyph(glyph);
    }

    public void draw(String s, double x, double y, int color, boolean shadow) {
        float[] rgba = ColorUtils.hashCodeToRGBA(color);
        if (shadow)
            drawInternal(s, (float) x, (float) y, new float[]{0, 0, 0, rgba[3]}, true);
        drawInternal(s, (float) x, (float) y, rgba, false);
    }

    public void drawInternal(String s, float x, float y, float[] color, boolean shadow) {
        if (prebakeGlyphsFuture != null && !prebakeGlyphsFuture.isDone()) {
            try {
                prebakeGlyphsFuture.get();
            } catch (InterruptedException | ExecutionException ignored) {}
        }

        float[] colors = new float[]{color[0], color[1], color[2]};

        glDisable(GL11.GL_CULL_FACE);

        char[] chars = s.toCharArray();
        float xOffset = 0;
        float yOffset = 0;
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];

            Glyph glyph = locateGlyph(c);
            if (glyph != null) {
                if (glyph.value != ' ') {
                    String i1 = glyph.owner.textureKey;
                    DrawEntry entry = new DrawEntry(xOffset, yOffset, colors[0], colors[1], colors[2], glyph);
                    GLYPH_PAGE_CACHE.computeIfAbsent(i1, integer -> new ArrayList<>()).add(entry);
                }
                xOffset += glyph.width;
            }
        }
        for (String textureKey : GLYPH_PAGE_CACHE.keySet()) {
            List<DrawEntry> objects = GLYPH_PAGE_CACHE.get(textureKey);

            for (DrawEntry object : objects) {
                float xo = object.atX;
                float yo = object.atY;
                float cr = object.r;
                float cg = object.g;
                float cb = object.b;
                Glyph glyph = object.toDraw;
                GlyphMap owner = glyph.owner;
                float w = glyph.width;
                float h = glyph.height;
                float u1 = (float) glyph.u / owner.width;
                float v1 = (float) glyph.v / owner.height;
                float u2 = (float) (glyph.u + glyph.width) / owner.width;
                float v2 = (float) (glyph.v + glyph.height) / owner.height;

                RenderHelper.drawTextureRect(xo, yo, xo + w, yo + h, u1, v1, u2, v2, TextureStorage.getTexture(textureKey), new Color(cr, cg, cb, color[3]));
            }
        }
    }

    public float getStringWidth(String text) {
        char[] c = stripControlCodes(text).toCharArray();
        float currentLine = 0;
        float maxPreviousLines = 0;
        for (char c1 : c) {
            if (c1 == '\n') {
                maxPreviousLines = Math.max(currentLine, maxPreviousLines);
                currentLine = 0;
                continue;
            }
            Glyph glyph = locateGlyph(c1);
            currentLine += glyph == null ? 0 : (glyph.width / (float) this.scaleMul);
        }
        return Math.max(currentLine, maxPreviousLines);
    }

    public float getStringHeight(String text) {
        char[] c = stripControlCodes(text).toCharArray();
        if (c.length == 0) {
            c = new char[]{' '};
        }
        float currentLine = 0;
        float previous = 0;
        for (char c1 : c) {
            if (c1 == '\n') {
                if (currentLine == 0) {
                    currentLine = (locateGlyph(' ') == null ? 0 : (Objects.requireNonNull(locateGlyph(' ')).height / (float) this.scaleMul));
                }
                previous += currentLine;
                currentLine = 0;
                continue;
            }
            Glyph glyph = locateGlyph(c1);
            currentLine = Math.max(glyph == null ? 0 : (glyph.height / (float) this.scaleMul), currentLine);
        }
        return currentLine + previous;
    }


    @Override
    public void close() {
        try {
            if (prebakeGlyphsFuture != null && !prebakeGlyphsFuture.isDone() && !prebakeGlyphsFuture.isCancelled()) {
                prebakeGlyphsFuture.cancel(true);
                prebakeGlyphsFuture.get();
                prebakeGlyphsFuture = null;
            }
            maps.forEach(GlyphMap::destroy);
            maps.clear();
            allGlyphs.clear();
            initialized = false;
        } catch (Exception ignored) {
        }
    }

    public Font getFont() {
        return font;
    }

    public float getOriginalSize() {
        return originalSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPaddingBetweenChars() {
        return paddingBetweenChars;
    }

    public String getPrebakeGlyphs() {
        return prebakeGlyphs;
    }

    public int getScaleMul() {
        return scaleMul;
    }

    public boolean isInitialized() {
        return initialized;
    }

    class DrawEntry {
        public final float atX;
        public final float atY;
        public final float r;
        public final float g;
        public final float b;
        public final Glyph toDraw;


        DrawEntry(float atX, float atY, float r, float g, float b, Glyph toDraw) {
            this.atX = atX;
            this.atY = atY;
            this.r = r;
            this.g = g;
            this.b = b;
            this.toDraw = toDraw;
        }
    }
}
