package com.ferra13671.Ferra2DEngine.Render.FontRenderer;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public final class FontUtils {

    public static Font createFont(InputStream inputStream, float size) throws IOException, FontFormatException {
        return Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(inputStream)).deriveFont(Font.PLAIN, size);
    }

    public static Font createFontNoThrow(InputStream inputStream, float size) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(inputStream)).deriveFont(Font.PLAIN, size);
        } catch (Exception ignored) {
            return null;
        }
    }
}
