package com.ferra13671.Ferra2DEngine.Render.TextRenderer;

import com.ferra13671.Ferra2DEngine.Render.RenderHelper;
import com.ferra13671.TextureUtils.GLTexture;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.FloatBuffer;

public class TextRenderer {
    private final GLTexture fontTexture1;
    private final GLTexture fontTexture4;
    private final float charSize = 1 / 16.0f;
    private final FloatBuffer wordWidth;
    private final FontMetrics fontMetrics;


    public TextRenderer(GLTexture texturePage1, GLTexture texturePage4) {
        this.fontTexture1 = texturePage1;
        this.fontTexture4 = texturePage4;
        fontTexture1.bind();
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        fontTexture1.unBind();

        fontTexture4.bind();
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        fontTexture4.unBind();

        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_3BYTE_BGR);
        Graphics graphics = image.getGraphics();
        graphics.setFont(new Font("DialogInput", Font.PLAIN, 100));
        fontMetrics = graphics.getFontMetrics();

        try (MemoryStack stack = MemoryStack.stackPush()) {
            wordWidth = stack.mallocFloat(Float.BYTES * 2048);
            for (int i = 0; i < 2048; i++) {
                switch (Character.toChars(i)[0]) {
                    case 'i':
                    case '!':
                    case ':':
                    case ';':
                    case '\'':
                    case ',':
                    case '.':
                    case '|':
                        wordWidth.put(i, 20f);
                        break;
                    case 'l':
                        wordWidth.put(i, 27f);
                        break;
                    default:
                        float b = fontMetrics.charWidth(i);
                        wordWidth.put(i, b);
                }
            }
        }
    }

    public float getStringWidth(TextWithSize text) {
        return getStringWidth(text.text, text.size);
    }

    public float getStringWidth(String string, int size) {
        CharSequence chars = string;

        float width = 0;

        for (int i = 0; i < chars.length(); i++) {
            width += (wordWidth.get(chars.charAt(i)) / 100) * size * 1.3f;
        }

        return width;
    }

    public void renderText(TextWithSize text, float x, float y) {
        renderText(text, x, y, Color.white);
    }

    public void renderText(TextWithSize text, float x, float y, Color color) {
        renderText(text.text, x, y, text.size, color);
    }

    public void renderText(CharSequence text, float x, float y, int size) {
        renderText(text, x, y, size, Color.white);
    }

    public void renderText(CharSequence text, float x, float y, int size, Color color) {
        GLTexture texture;
        int a = text.length();
        for (int i = 0; i < a; i++) {
            char word = text.charAt(i);
            float width = (wordWidth.get(word) / 100) * size * 1.3f;
            int y2 = word >> 4;
            int x2 = word & 0b1111;
            if (y2 > 63) {
                texture = fontTexture4;
                y2 = y2 - 64;
            } else {
                texture = fontTexture1;
            }
            float[] b = {0,0,0,0};
            b[0] = x2 * charSize;
            b[1] = b[0] + charSize;
            b[2] = y2 * charSize;
            b[3] = b[2] + charSize;
            RenderHelper.drawTextureRect(x, y, x + size, y + size, b[0], b[2], b[1], b[3], texture, color);
            x = x + width;
        }
    }
}
