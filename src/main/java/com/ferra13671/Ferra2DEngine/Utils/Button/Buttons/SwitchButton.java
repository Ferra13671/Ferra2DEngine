package com.ferra13671.Ferra2DEngine.Utils.Button.Buttons;


import com.ferra13671.Ferra2DEngine.Render.RenderHelper;
import com.ferra13671.Ferra2DEngine.Render.TextRenderer.TextWithSize;
import com.ferra13671.Ferra2DEngine.Utils.Button.Button;

public class SwitchButton extends Button {
    private boolean bVal;

    public SwitchButton(int id, int centerX, int centerY, int width, int height, boolean defaultBVal, TextWithSize text) {
        super(id, centerX, centerY, width, height, text);

        bVal = defaultBVal;
    }


    @Override
    public void renderButton() {
        if (!this.hovered) {
            RenderHelper.drawRect(getCenterX() - getWidth(), getCenterY() - getHeight(), getCenterX() + getWidth(), getCenterY() + getHeight(), rectColor);

            RenderHelper.drawText(getText(), getCenterX() - getHalfTextWidth(), getCenterY() - (getText().size / 2f));
        } else {
            RenderHelper.drawRect(getCenterX() - getWidth() - 1, getCenterY() - getHeight() - 1, getCenterX() + getWidth() + 1, getCenterY() + getHeight() + 1, rectColor);

            RenderHelper.drawHorizontalGradientRect((int)(getCenterX() - (getWidth() * 0.8)), getCenterY() + getHeight() - 4, getCenterX(), getCenterY() + getHeight() - 2, alphaColor, whiteColor);
            RenderHelper.drawHorizontalGradientRect(getCenterX(), getCenterY() + getHeight() - 4, (int)(getCenterX() + (getWidth() * 0.8)), getCenterY() + getHeight() - 2, whiteColor, alphaColor);
            RenderHelper.drawText(getText(), getCenterX() - getHalfTextWidth(), getCenterY() - (getText().size / 2f));
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isMouseOnButton(mouseX, mouseY)) {
            bVal = !bVal;
        }
    }

    @Override
    public TextWithSize getText() {
        return new TextWithSize(text + ": " + (bVal ? "ON" : "OFF"), text.size);
    }

    public float getHalfTextWidth() {
        return (RenderHelper.textRenderer.getStringWidth(getText()) / 2f);
    }

    public boolean getBVal() {
        return bVal;
    }
}
