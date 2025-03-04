package com.ferra13671.Ferra2DEngine.Render;

import com.ferra13671.Ferra2DEngine.Render.FontRenderer.FontRenderer;
import com.ferra13671.Ferra2DEngine.Render.FontRenderer.FontUtils;
import com.ferra13671.Ferra2DEngine.Utils.Math.FerraMath;
import com.ferra13671.Ferra2DEngine.Utils.RainbowUtils;
import com.ferra13671.TextureUtils.GLTexture;
import org.lwjgl.system.MemoryUtil;

import java.awt.*;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.text.DecimalFormat;

import static com.ferra13671.Ferra2DEngine.Utils.ColorUtils.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * A class that contains methods for rendering various shapes or textures more easily.
 *
 * @author Ferra13671
 */

public class RenderHelper {
    private static final FloatBuffer customTextureCords = MemoryUtil.memAlloc(8 * Float.BYTES).order(ByteOrder.nativeOrder()).asFloatBuffer();
    private static final FloatBuffer rectVertex = MemoryUtil.memAlloc(8 * Float.BYTES).order(ByteOrder.nativeOrder()).asFloatBuffer();

    public static FontRenderer fontRenderer;

    public static final DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    private static boolean initialized = false;

    public static void init() {
        if (!initialized) {
            fontRenderer = new FontRenderer(FontUtils.createFontNoThrow(RenderHelper.class.getClassLoader().getResourceAsStream("defaultFont.ttf"), 17), 30);
            initialized = true;
        }
    }



    /**
     * Enables blend(for normal rendering of transparency).

     * <b> In the {@code drawTextureRect(float x1, float y1, float x2, ...)}
     * and {@code drawCustomTextureSizeRect(float x1, float y1, float x2, ...)}
     * methods, this is done automatically. </b>
     */
    public static void enableBlend() {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }


    /**
     * Disables blend.
     */
    public static void disableBlend() {
        glDisable(GL_BLEND);
    }

    /**
     * Draws a rectangle at the specified coordinates.
     *
     * @param x1   Initial x coordinate.
     * @param y1   Initial y coordinate.
     * @param x2   End x coordinate.
     * @param y2   End y coordinate.
     * @param color   Rectangle color.
     */
    public static void drawRect(float x1, float y1, float x2, float y2, int color) {
        draw4ColorRect(x1,y1,x2,y2,color,color,color,color);
    }

    /**
     * Draws a rectangle with a vertical gradient at the specified coordinates.
     *
     * @param x1   Initial x coordinate.
     * @param y1   Initial y coordinate.
     * @param x2   End x coordinate.
     * @param y2   End y coordinate.
     * @param color1   y1 color.
     * @param color2   y2 color.
     */
    public static void drawVerticalGradientRect(float x1, float y1, float x2, float y2, int color1, int color2) {
        draw4ColorRect(x1,y1,x2,y2, color1, color2, color1, color2);
    }

    /**
     * Draws a rectangle with a horizontal gradient at the specified coordinates.
     *
     * @param x1   Initial x coordinate.
     * @param y1   Initial y coordinate.
     * @param x2   End x coordinate.
     * @param y2   End y coordinate.
     * @param color1   x1 color.
     * @param color2   x2 color.
     */
    public static void drawHorizontalGradientRect(float x1, float y1, float x2, float y2, int color1, int color2) {
        draw4ColorRect(x1,y1,x2,y2, color1, color1, color2, color2);
    }

    /**
     * Draws a four-color rectangle at the given coordinates.
     *
     * @param x1   Initial x coordinate.
     * @param y1   Initial y coordinate.
     * @param x2   End x coordinate.
     * @param y2   End y coordinate.
     * @param x1y1Color   x1 y1 color.
     * @param x1y2Color   x1 y2 color.
     * @param x2y1Color   x2 y1 color.
     * @param x2y2Color   x2 y2 color.
     */
    public static void draw4ColorRect(float x1, float y1, float x2, float y2, int x1y1Color, int x1y2Color, int x2y1Color, int x2y2Color) {
        glPushMatrix();

        float[] x1y1C = hashCodeToRGBA(x1y1Color);
        float[] x2y1C = hashCodeToRGBA(x2y1Color);
        float[] x1y2C = hashCodeToRGBA(x1y2Color);
        float[] x2y2C = hashCodeToRGBA(x2y2Color);

        enableBlend();
        glBegin(GL_QUADS);

        glColor4f(x1y1C[0], x1y1C[1], x1y1C[2], x1y1C[3]); glVertex2f(x1,y1);
        glColor4f(x1y2C[0], x1y2C[1], x1y2C[2], x1y2C[3]); glVertex2f(x1,y2);
        glColor4f(x2y2C[0], x2y2C[1], x2y2C[2], x2y2C[3]); glVertex2f(x2,y2);
        glColor4f(x2y1C[0], x2y1C[1], x2y1C[2], x2y1C[3]); glVertex2f(x2,y1);
        glEnd();
        glPopMatrix();
        disableBlend();
    }


    /**
     * Draws a rectangle with a texture at the given coordinates.
     *
     * @param x1   Initial x coordinate.
     * @param y1   Initial y coordinate.
     * @param x2   End x coordinate.
     * @param y2   End y coordinate.
     * @param texture   The texture that will be applied to the rectangle.
     * @param inverted   Whether texture coordinates will be inverted or not.
     */
    public static void drawTextureRect(float x1, float y1, float x2, float y2, GLTexture texture, boolean inverted) {
        drawTextureRect(x1,y1,x2,y2,0,0,1,1, texture, -1, inverted);
    }


    /**
     * Draws a rectangle with a texture at the given coordinates.
     *
     * @param x1   Initial x coordinate.
     * @param y1   Initial y coordinate.
     * @param x2   End x coordinate.
     * @param y2   End y coordinate.
     * @param texture   The texture that will be applied to the rectangle.
     */
    public static void drawTextureRect(float x1, float y1, float x2, float y2, GLTexture texture) {
        drawTextureRect(x1,y1,x2,y2,0,0,1,1, texture, -1, true);
    }


    /**
     * Draws a rectangle with a texture at the given coordinates and color.
     *
     * @param x1   Initial x coordinate.
     * @param y1   Initial y coordinate.
     * @param x2   End x coordinate.
     * @param y2   End y coordinate.
     * @param texture   The texture that will be applied to the rectangle.
     * @param color   The color to be applied to the texture. If the color is white, nothing will change,
     *               if the color is gray, the texture will be shaded depending on the brightness of the color,
     *               and if the color is black, the texture will not be visible.
     * @param inverted   Whether texture coordinates will be inverted or not.
     */
    public static void drawTextureRect(float x1, float y1, float x2, float y2, GLTexture texture, int color, boolean inverted) {
        drawTextureRect(x1,y1,x2,y2,0,0,1,1, texture, color, inverted);
    }


    /**
     * Draws a rectangle with a texture at the given coordinates and color.
     *
     * @param x1   Initial x coordinate.
     * @param y1   Initial y coordinate.
     * @param x2   End x coordinate.
     * @param y2   End y coordinate.
     * @param texture   The texture that will be applied to the rectangle.
     * @param color   The color to be applied to the texture. If the color is white, nothing will change,
     *               if the color is gray, the texture will be shaded depending on the brightness of the color,
     *               and if the color is black, the texture will not be visible.
     */
    public static void drawTextureRect(float x1, float y1, float x2, float y2, GLTexture texture, int color) {
        drawTextureRect(x1,y1,x2,y2,0,0,1,1, texture, color, true);
    }


    /**
     * Draws a rectangle with a custom texture position.
     * Suitable for those who use 1 texture as a texture atlas.
     *
     * @param x1   Initial x coordinate.
     * @param y1   Initial y coordinate.
     * @param x2   End x coordinate.
     * @param y2   End y coordinate.
     * @param texPosX1   Initial x coordinate inside the texture. (real number from 0 to 1)
     * @param texPosY1   Initial y coordinate inside the texture. (real number from 0 to 1)
     * @param texPosX2   End x coordinate inside the texture. (real number from 0 to 1)
     * @param texPosY2   End y coordinate inside the texture. (real number from 0 to 1)
     * @param texture   The texture that will be applied to the rectangle.
     * @param inverted   Whether texture coordinates will be inverted or not.
     */
    public static void drawTextureRect(float x1, float y1, float x2, float y2, float texPosX1, float texPosY1, float texPosX2, float texPosY2, GLTexture texture, boolean inverted) {
        drawTextureRect(x1,y1,x2,y2,texPosX1,texPosY1,texPosX2,texPosY2,texture, -1, inverted);
    }


    /**
     * Draws a rectangle with a custom texture position.
     * Suitable for those who use 1 texture as a texture atlas.
     *
     * @param x1   Initial x coordinate.
     * @param y1   Initial y coordinate.
     * @param x2   End x coordinate.
     * @param y2   End y coordinate.
     * @param texPosX1   Initial x coordinate inside the texture. (real number from 0 to 1)
     * @param texPosY1   Initial y coordinate inside the texture. (real number from 0 to 1)
     * @param texPosX2   End x coordinate inside the texture. (real number from 0 to 1)
     * @param texPosY2   End y coordinate inside the texture. (real number from 0 to 1)
     * @param texture   The texture that will be applied to the rectangle.
     */
    public static void drawTextureRect(float x1, float y1, float x2, float y2, float texPosX1, float texPosY1, float texPosX2, float texPosY2, GLTexture texture) {
        drawTextureRect(x1,y1,x2,y2,texPosX1,texPosY1,texPosX2,texPosY2,texture, -1, true);
    }


    /**
     * Draws a rectangle with the custom texture position and color.
     * Suitable for those who use 1 texture as a texture atlas.
     *
     * @param x1   Initial x coordinate.
     * @param y1   Initial y coordinate.
     * @param x2   End x coordinate.
     * @param y2   End y coordinate.
     * @param texPosX1   Initial x coordinate inside the texture. (real number from 0 to 1)
     * @param texPosY1   Initial y coordinate inside the texture. (real number from 0 to 1)
     * @param texPosX2   End x coordinate inside the texture. (real number from 0 to 1)
     * @param texPosY2   End y coordinate inside the texture. (real number from 0 to 1)
     * @param texture   The texture that will be applied to the rectangle.
     * @param color   The color to be applied to the texture. If the color is white, nothing will change,
     *               if the color is gray, the texture will be shaded depending on the brightness of the color,
     *               and if the color is black, the texture will not be visible.
     */
    public static void drawTextureRect(float x1, float y1, float x2, float y2, float texPosX1, float texPosY1, float texPosX2, float texPosY2, GLTexture texture, int color) {
        drawTextureRect(x1, y1, x2, y2, texPosX1, texPosY1, texPosX2, texPosY2, texture, color, true);
    }


    /**
     * Draws a rectangle with the custom texture position and color.
     * Suitable for those who use 1 texture as a texture atlas.
     *
     * @param x1   Initial x coordinate.
     * @param y1   Initial y coordinate.
     * @param x2   End x coordinate.
     * @param y2   End y coordinate.
     * @param texPosX1   Initial x coordinate inside the texture. (real number from 0 to 1)
     * @param texPosY1   Initial y coordinate inside the texture. (real number from 0 to 1)
     * @param texPosX2   End x coordinate inside the texture. (real number from 0 to 1)
     * @param texPosY2   End y coordinate inside the texture. (real number from 0 to 1)
     * @param texture   The texture that will be applied to the rectangle.
     * @param color   The color to be applied to the texture. If the color is white, nothing will change,
     *               if the color is gray, the texture will be shaded depending on the brightness of the color,
     *               and if the color is black, the texture will not be visible.
     * @param inverted   Whether texture coordinates will be inverted or not.
     */
    public static void drawTextureRect(float x1, float y1, float x2, float y2, float texPosX1, float texPosY1, float texPosX2, float texPosY2, GLTexture texture, int color, boolean inverted) {
        rectVertex.put(new float[]{x1,y1, x2,y1, x2,y2, x1,y2}).position(0);
        if (inverted)
            customTextureCords.put(new float[]{texPosX1, texPosY1,   texPosX2, texPosY1,   texPosX2, texPosY2,   texPosX1, texPosY2}).position(0);
        else
            customTextureCords.put(new float[]{texPosX2, texPosY2,   texPosX1, texPosY2,   texPosX1, texPosY1,   texPosX2, texPosY1}).position(0);

        enableBlend();

        float[] c = hashCodeToRGBA(color);

        glColor4f(c[0],c[1],c[2], c[3]);
        glPushMatrix();

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);

        glVertexPointer(2, GL_FLOAT, 0, rectVertex);
        glTexCoordPointer(2, GL_FLOAT, 0, customTextureCords);

        texture.bind();
        glDrawArrays(GL_TRIANGLE_FAN, 0, 4);

        glDisableClientState(GL_VERTEX_ARRAY);
        glDisableClientState(GL_TEXTURE_COORD_ARRAY);

        glDisable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);

        glPopMatrix();
        disableBlend();
    }


    /**
     * Draws a triangle at the specified coordinates.
     *
     * @param x1   x coordinate of the first point.
     * @param y1   y coordinate of the first point
     * @param x2   x coordinate of the second point.
     * @param y2   y coordinate of the second point.
     * @param x3   x coordinate of the third point.
     * @param y3   y coordinate of the third point.
     * @param color   Triangle color.
     */
    public static void drawTriangle(float x1, float y1, float x2, float y2, float x3, float y3, int color) {
        draw3ColorTriangle(x1,y1,x2,y2,x3,y3,color,color,color);
    }

    /**
     * Draws a three-color triangle at the specified coordinates.
     *
     * @param x1   x coordinate of the first point.
     * @param y1   y coordinate of the first point
     * @param x2   x coordinate of the second point.
     * @param y2   y coordinate of the second point.
     * @param x3   x coordinate of the third point.
     * @param y3   y coordinate of the third point.
     * @param color1   The color of the first point.
     * @param color2   The color of the second point.
     * @param color3   The color of the third point.
     */
    public static void draw3ColorTriangle(float x1, float y1, float x2, float y2, float x3, float y3, int color1, int color2, int color3) {
        glPushMatrix();

        float[] c1 = hashCodeToRGBA(color1);
        float[] c2 = hashCodeToRGBA(color2);
        float[] c3 = hashCodeToRGBA(color3);

        enableBlend();
        glBegin(GL_TRIANGLES);
        glColor4f(c1[0], c1[1], c1[2], c1[3]); glVertex2f(x1,y1);
        glColor4f(c2[0], c2[1], c2[2], c2[3]); glVertex2f(x2,y2);
        glColor4f(c3[0], c3[1], c3[2], c3[3]); glVertex2f(x3,y3);
        glEnd();

        glPopMatrix();
        disableBlend();
    }


    /**
     * Draws a circle at the specified coordinates and size.
     *
     * @param x   Circle center by x coordinates.
     * @param y   Circle center by y coordinates.
     * @param size   Circle size.
     * @param color   Circle color.
     */
    public static void drawCircle(float x, float y, float size, int color) {
        glPushMatrix();
        glTranslatef(x, y, 0);

        double x1,y1;
        byte cnt = 40;
        float a = (float)Math.PI * 2 / cnt;

        enableBlend();
        glBegin(GL_TRIANGLE_FAN);

        float[] c = hashCodeToRGBA(color);

        glColor4f(c[0], c[1],c[2], c[3]);
        glVertex2d(0,0);
        for (int i = -1; i < cnt; i++) {
            x1 = Math.sin(a * i) * size;
            y1 = Math.cos(a * i) * size;
            glVertex2d(x1,y1);
        }
        glEnd();
        glPopMatrix();
        disableBlend();
    }

    /**
     * Draws an oval at the specified coordinates and dimensions.
     *
     * @param x   Center of the oval at x coordinates.
     * @param y   Center of the oval at y coordinates.
     * @param widthSize   Size by x coordinates.
     * @param heightSize   Size by y coordinates.
     * @param color   The color of this oval.
     */
    public static void drawOval(float x, float y, float widthSize, float heightSize, int color) {
        drawOval(x, y, widthSize, heightSize, 0, 0, 0, color);
    }


    /**
     * Draws an oval in the specified coordinates and dimensions.
     * Also inputs rotation values as input data.
     *
     * @param x   Center of the oval at x coordinates.
     * @param y   Center of the oval at y coordinates.
     * @param widthSize   Size by x coordinates.
     * @param heightSize   Size by y coordinates.
     * @param xRotate   Rotation in X coordinate.
     * @param yRotate   Rotation in Y coordinate
     * @param zRotate   Rotation in Z coordinate
     * @param color   The color of this oval.
     */
    public static void drawOval(float x, float y, float widthSize, float heightSize, double xRotate, double yRotate, double zRotate, int color) {
        glPushMatrix();
        glTranslatef(x, y, 0);
        glRotated(xRotate, 1, 0, 0);
        glRotated(yRotate, 0, 1, 0);
        glRotated(zRotate, 0, 0, 1);

        double x1,y1;
        byte cnt = 40;
        float a = (float)Math.PI * 2 / cnt;

        enableBlend();
        glBegin(GL_TRIANGLE_FAN);

        float[] c = hashCodeToRGBA(color);

        glColor4f(c[0], c[1],c[2], c[3]);
        glVertex2d(0,0);
        for (int i = -1; i < cnt; i++) {
            x1 = Math.sin(a * i) * widthSize;
            y1 = Math.cos(a * i) * heightSize;
            glVertex2d(x1,y1);
        }
        glEnd();
        glRotated(-xRotate, 1, 0, 0);
        glRotated(-yRotate, 0, 1, 0);
        glRotated(-zRotate, 0, 0, 1);
        glPopMatrix();
        disableBlend();
    }


    /**
     * Draws a shape with a custom number of corners. Needed when you need to draw, for example, an octagon.
     *
     * @param x   Center of the figure at x coordinates.
     * @param y   Center of the figure at y coordinates.
     * @param size   Figure Size.
     * @param angles   The number of angles that will be in the figure.
     *                If the number of corners is less than 3, the method will not draw anything,
     *                and if the number of corners is 4, it will call the {@code drawRect} method.
     * @param color   Figure color.
     */
    public static void drawFigureWithCustomNumberOfAngles(float x, float y, float size,int angles, int color) {
        if (angles == 4) {
            drawRect(x - size, y - size, x + size, y + size, color);
        } else if (angles < 3) return;

        glPushMatrix();
        glTranslatef(x, y, 0);

        double x1,y1;
        float a = (float)Math.PI * 2 / angles;

        enableBlend();
        glBegin(GL_TRIANGLE_FAN);

        float[] c = hashCodeToRGBA(color);

        glColor4f(c[0], c[1],c[2], c[3]);
        glVertex2d(0,0);
        for (int i = -1; i < angles; i++) {
            x1 = FerraMath.sin_rad(a * i) * size;
            y1 = FerraMath.cos_rad(a * i) * size;
            glVertex2d(x1,y1);
        }
        glEnd();
        glPopMatrix();
        disableBlend();
    }


    /**
     * Draws a rainbow rectangle according to specified coordinates, rainbow speed mode and standard quality.
     *
     * @param x1   Initial x coordinate.
     * @param y1   Initial y coordinate.
     * @param x2   End x coordinate.
     * @param y2   End y coordinate.
     * @param rainbowType   Rainbow Speed Mode.
     *
     * @implNote <b> FAST and VERYFAST modes are not recommended because color breaks are noticeable when using them. </b>
     */
    public static void drawGradientRainbowRect(int x1, int y1, int x2, int y2, RainbowUtils.RainbowRectMode rainbowType) {
        drawGradientRainbowRect( x1, y1, x2, y2, rainbowType, 1);
    }


    /**
     * Draws a rainbow rectangle according to the specified coordinates, rainbow speed mode, and specified quality.
     *
     * @param x1   Initial x coordinate.
     * @param y1   Initial y coordinate.
     * @param x2   End x coordinate.
     * @param y2   End y coordinate.
     * @param rainbowType   Rainbow Speed Mode.
     * @param qualityFactor   Factor that determines the quality of the rainbow(default 1).
     *
     * @note <b> FAST and VERYFAST modes are not recommended because color breaks are noticeable when using them. </b>
     * <p></p>
     * For small rectangles standard quality is sufficient, but for large rectangles it is better to use quality 3.
     * However, quality strongly affects performance, so it is not recommended to set it higher than 4-5.
     */
    public static void drawGradientRainbowRect(int x1, int y1, int x2, int y2, RainbowUtils.RainbowRectMode rainbowType, float qualityFactor) {
        final float[] counter = {1};
        int dX;
        int tX = x1;
        short delay = rainbowType.delay;
        float speed = rainbowType.speed;
        short factor = (short) (50 * qualityFactor);
        float counterFactor = 1 / qualityFactor;

        float fX;

        if (x1 < x2) {
            fX = x2 - x1;
            fX /= factor;
        } else {
            fX = x1 - x2;
            fX = -(fX / factor);
        }
        dX = fX != 0 ? (int) Math.ceil(fX) : 0;

        glPushMatrix();

        enableBlend();
        while (tX != x2) {

            if (x1 < x2) {
                if (tX + dX > x2) {
                    dX = x2 - tX;
                }
            } else {
                if (tX + dX < x2) {
                    dX = tX - x2;
                }
            }

            int color = RainbowUtils.getCurrentRainbow((int)(counter[0] * delay), speed);

            glBegin(GL_QUADS);

            float[] c = hashCodeToRGBA(color);

            glColor4f(c[0], c[1],c[2], c[3]);
            glVertex2f(tX,y1);
            glVertex2f(tX,y2);
            glVertex2f(tX + dX,y2);
            glVertex2f(tX + dX,y1);
            glEnd();

            tX += dX;
            counter[0] += counterFactor;
        }

        glPopMatrix();
        disableBlend();
    }


    /**
     * Draws the outline of a rectangle according to specified coordinates, line width and color.
     *
     * @param x1   Initial x coordinate.
     * @param y1   Initial y coordinate.
     * @param x2   End x coordinate.
     * @param y2   End y coordinate.
     * @param lineWidth   Line width.
     * @param color   The color of this outline rect.
     */
    public static void drawOutlineRect(float x1, float y1, float x2, float y2, int lineWidth, int color) {
        int outlineX;
        int outlineY;
        outlineX = x1 > x2 ? -lineWidth : lineWidth;
        outlineY = y1 > y2 ? lineWidth : -lineWidth;

        drawRect(x1,y1, x1 + outlineX, y2, color);
        drawRect(x1 + outlineX, y2, x2, y2 + outlineY, color);
        drawRect(x2, y2 + outlineY, x2 - outlineX, y1, color);
        drawRect(x2 - outlineX, y1, x1, y1 - outlineY, color);
    }


    /**
     * Draws text starting at the specified coordinates, size and color.
     *
     * @param text   Text to be drawn.
     * @param x   Initial x coordinate.
     * @param y   Initial y coordinate.
     * @param size   Text size(default = 1).
     * @param color   Text Color.
     */
    public static void drawText(String text, float x, float y, float size, int color) {
        drawText(text, x, y, size, color, false);
    }

    /**
     * Draws text starting at the specified coordinates, size, color and shadow.
     *
     * @param text   Text to be drawn.
     * @param x   Initial x coordinate.
     * @param y   Initial y coordinate.
     * @param size   Text size(default = 1).
     * @param color   Text Color.
     * @param shadow   Does the shadow behind the text need to be rendered.
     */
    public static void drawText(String text, float x, float y, float size, int color, boolean shadow) {
        if (size != 1) {
            glPushMatrix();
            glScalef(size, size, 1);
        }
        fontRenderer.draw(text, x / size, y / size, color, shadow);
        if (size != 1) glPopMatrix();
    }

    /**
     * Draws text starting at the specified coordinates and size. White is used as the color.
     *
     * @param text   Text to be drawn.
     * @param x   Initial x coordinate.
     * @param y   Initial y coordinate.
     * @param size   Text size(default = 1).
     */
    public static void drawText(String text, float x, float y, int size) {
        if (size != 1) {
            glPushMatrix();
            glScalef(size, size, 1);
        }
        fontRenderer.draw(text, x / size, y / size, -1, false);
        if (size != 1) glPopMatrix();
    }
}
