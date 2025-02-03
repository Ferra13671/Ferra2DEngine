package com.ferra13671.Ferra2DEngine.Utils;

import com.ferra13671.Ferra2DEngine.Utils.Math.FerraMath;

import java.awt.*;

public final class ColorUtils {
    public static final int TRANSPARENT = fastRGBA(0, 0, 0, 0);
    public static final int BLACK = fastRGBA(0, 0, 0, 255);
    public static final int WHITE = fastRGBA(255, 255, 255, 255);

    public static int integrateAlpha(int colorHashcode, int alpha) {
        int red = (colorHashcode >> 16 & 255);
        int green = (colorHashcode >> 8 & 255);
        int blue = (colorHashcode & 255);

        return new Color(red, green, blue, alpha).hashCode();
    }

    public static int fastRGBA(int red, int green, int blue, int alpha) {
        return ((FerraMath.applyRange(alpha, 0, 255) & 0xFF) << 24) |
                ((FerraMath.applyRange(red, 0, 255) & 0xFF) << 16) |
                ((FerraMath.applyRange(green, 0, 255) & 0xFF) << 8)  |
                ((FerraMath.applyRange(blue, 0, 255) & 0xFF) << 0);
    }

    public static float[] hashCodeToRGB(int hashCode) {
        return new float[]{
                (float) (hashCode >> 16 & 255) / 255.0F,
                (float) (hashCode >> 8 & 255) / 255.0F,
                (float) (hashCode & 255) / 255.0F
        };
    }

    public static float[] hashCodeToRGBA(int hashCode) {
        return new float[]{
                (float) (hashCode >> 16 & 255) / 255.0F,
                (float) (hashCode >> 8 & 255) / 255.0F,
                (float) (hashCode & 255) / 255.0F,
                (float) (hashCode >>> 24) / 255.0F
        };
    }
}
