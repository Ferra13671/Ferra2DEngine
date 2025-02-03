package com.ferra13671.Ferra2DEngine.Render.FontRenderer;

public class Glyph {
    public final int u;
    public final int v;
    public final int width;
    public final int height;
    public final char value;
    public final GlyphMap owner;


    protected Glyph(int u, int v, int width, int height, char value, GlyphMap owner) {
        this.u = u;
        this.v = v;
        this.width = width;
        this.height = height;
        this.value = value;
        this.owner = owner;
    }
}
