package com.ferra13671.Ferra2DEngine.Render.TextRenderer;

public class AppearingText extends TextWithSize {

    private final String[] symbols;
    private int nextSymbol = 0;
    private int delayTicks = 0;
    private final int speedTicks;
    private final int maxSymbols;

    public AppearingText(String text, int size, int speedTicks) {
        super(text, size);
        this.text = "";

        this.speedTicks = speedTicks;
        CharSequence charSequence = text;
        symbols = new String[charSequence.length()];
        int maxSymbols = 0;
        for (int i = 0; i < charSequence.length(); i++) {
            symbols[i] = Character.toString(charSequence.charAt(i));
            maxSymbols++;
        }
        this.maxSymbols = maxSymbols;
    }

    public void tick() {
        if (nextSymbol >= maxSymbols) return;
        delayTicks++;
        if (delayTicks >= speedTicks) {
            delayTicks = 0;
            text = text + symbols[nextSymbol];
            nextSymbol++;
        }
    }
}
