package com.ferra13671.Ferra2DEngine.Utils;

import java.awt.*;

public final class ColorUtils {

    public static int integrateAlpha(int colorHashcode, int alpha) {
        int red = (colorHashcode >> 16 & 255);
        int green = (colorHashcode >> 8 & 255);
        int blue = (colorHashcode & 255);

        return new Color(red, green, blue, alpha).hashCode();
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
