package com.ferra13671.Ferra2DEngine.Utils.Button.buttons;

import com.ferra13671.Ferra2DEngine.Render.RenderHelper;
import com.ferra13671.Ferra2DEngine.Utils.Button.Button;

public class SwitchButton extends Button {
    private boolean bVal;

    public SwitchButton(int id, int centerX, int centerY, int width, int height, boolean defaultBVal, String text) {
        super(id, centerX, centerY, width, height, text);

        this.bVal = defaultBVal;
    }


    @Override
    public void renderButton() {
        drawPlate(getAnimationDelta());
        RenderHelper.drawText(this.getText(), this.getCenterX() - this.getHalfTextWidth(), this.getCenterY() - (RenderHelper.fontRenderer.getStringHeight(getText()) / 2f), 1, -1, true);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (isMouseOnButton(mouseX, mouseY)) {
                this.bVal = !this.bVal;
            }
        }
    }

    @Override
    public String getText() {
        return this.text + ": " + (this.bVal ? "ON" : "OFF");
    }

    public float getHalfTextWidth() {
        return (RenderHelper.fontRenderer.getStringWidth(this.getText()) / 2f);
    }

    public boolean getBVal() {
        return this.bVal;
    }
}
