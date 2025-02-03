package com.ferra13671.Ferra2DEngine.Utils.Button.buttons;

import com.ferra13671.Ferra2DEngine.Render.RenderHelper;
import com.ferra13671.Ferra2DEngine.Utils.Button.Button;

import java.util.ArrayList;

public class ModeButton extends Button {
    private final ArrayList<String> options;

    int index = 0;
    String sVal;

    public ModeButton(int id, int centerX, int centerY, int width, int height, String text, ArrayList<String> options) {
        super(id, centerX, centerY, width, height, text);

        this.options = options;

        this.sVal = this.options.get(index);
    }

    @Override
    public void renderButton() {
        drawPlate(getAnimationDelta());
        RenderHelper.drawText(this.getText(), (int) (this.getCenterX() - this.getHalfTextWidth()), this.getCenterY() - (RenderHelper.fontRenderer.getStringHeight(getText()) / 2f), 1, -1, true);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (!isMouseOnButton(mouseX, mouseY)) return;

        if (index >= (options.size() - 1))
            index = 0;
        else
            index++;

        this.sVal = options.get(index);
    }

    @Override
    public String getText() {
        return this.text + ": " + this.sVal;
    }


    public String getsVal() {
        return this.sVal;
    }

    public float getHalfTextWidth() {
        return (RenderHelper.fontRenderer.getStringWidth(this.getText()) / 2f);
    }
}
