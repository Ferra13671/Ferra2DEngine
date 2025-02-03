package com.ferra13671.Ferra2DEngine.Utils.Button.buttons;

import com.ferra13671.Ferra2DEngine.Utils.Button.Button;

public class ButtonWithOffset extends Button {

    private int offset;

    public ButtonWithOffset(int id, int x, int y, int width, int height, String text) {
        super(id, x, y, width, height, text);
    }

    public void setOffset(int newOffset) {
        offset = newOffset;
    }

    @Override
    public int getCenterY() {
        return centerY + ((offset * (getHeight() * 2)));
    }
}
