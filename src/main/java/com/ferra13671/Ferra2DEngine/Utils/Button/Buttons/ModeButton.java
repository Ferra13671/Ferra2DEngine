package com.ferra13671.Ferra2DEngine.Utils.Button.Buttons;


import com.ferra13671.Ferra2DEngine.Render.RenderHelper;
import com.ferra13671.Ferra2DEngine.Render.TextRenderer.TextWithSize;
import com.ferra13671.Ferra2DEngine.Utils.Button.Button;

import java.util.ArrayList;

public class ModeButton extends Button {
    private final ArrayList<String> options;

    int index = 0;
    String sVal;

    public ModeButton(int id, int centerX, int centerY, int width, int height, TextWithSize text, ArrayList<String> options) {
        super(id, centerX, centerY, width, height, text);

        this.options = options;

        this.sVal = this.options.get(index);
    }

    @Override
    public void renderButton() {
        if (!this.hovered) {
            RenderHelper.drawRect(getCenterX() - getWidth(), getCenterY() - getHeight(), getCenterX() + getWidth(), getCenterY() + getHeight(), rectColor);

            RenderHelper.drawText(getText(), (int) (getCenterX() - getHalfTextWidth()), getCenterY() - (text.size / 2f));
        } else {
            RenderHelper.drawRect(getCenterX() - getWidth() - 1, getCenterY() - getHeight() - 1, getCenterX() + getWidth() + 1, getCenterY() + getHeight() + 1, rectColor);

            RenderHelper.drawHorizontalGradientRect((int)(getCenterX() - (getWidth() * 0.8)), getCenterY() + getHeight() - 4, getCenterX(), getCenterY() + getHeight() - 2, alphaColor, whiteColor);
            RenderHelper.drawHorizontalGradientRect(getCenterX(), getCenterY() + getHeight() - 4, (int)(getCenterX() + (getWidth() * 0.8)), getCenterY() + getHeight() - 2, whiteColor, alphaColor);
            RenderHelper.drawText(getText(), (int) (getCenterX() - getHalfTextWidth()), getCenterY() - (text.size / 2f));
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {

        if (!isMouseOnButton(mouseX, mouseY)) return;

        if (index >= (options.size() - 1))
            index = 0;
        else
            index++;

        this.sVal = options.get(index);
    }

    @Override
    public TextWithSize getText() {
        return new TextWithSize(text.text + ": " + sVal, text.size);
    }


    public String getsVal() {
        return this.sVal;
    }

    public float getHalfTextWidth() {
        return (RenderHelper.textRenderer.getStringWidth(getText()) / 2f);
    }
}
