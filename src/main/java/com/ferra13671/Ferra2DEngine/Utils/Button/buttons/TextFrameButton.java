package com.ferra13671.Ferra2DEngine.Utils.Button.buttons;

import com.ferra13671.Ferra2DEngine.IO.KeyboardUtils;
import com.ferra13671.Ferra2DEngine.Render.RenderHelper;
import com.ferra13671.Ferra2DEngine.Utils.Button.Button;

public class TextFrameButton extends Button {
    private StringBuilder textBuilder = new StringBuilder();

    private boolean selected = false;


    public TextFrameButton(int id, int centerX, int centerY, int width, int height) {
        super(id,centerX,centerY, width, height, "");
    }


    @Override
    public void renderButton() {
        RenderHelper.drawRect(this.getCenterX() - this.getWidth(), this.getCenterY() - this.getHeight(), this.getCenterX() + this.getWidth(), this.getCenterY() + this.getHeight(), RECT_COLOR);

        RenderHelper.drawText(textBuilder.toString(), this.getCenterX() - this.getWidth() + 3, this.getCenterY() - (RenderHelper.fontRenderer.getStringHeight(getText()) / 2f), 1, -1, true);
    }

    @Override
    public void keyTyped(int key) {

        if (!this.selected) return;

        if (key == KeyboardUtils.KEY_BACKSPACE) {
            if (textBuilder.length() > 0) {
                textBuilder.deleteCharAt(textBuilder.length() - 1);
            }
        }
    }

    @Override
    public void charTyped(char _char) {

        if (!this.selected) return;

        if (RenderHelper.fontRenderer.getStringWidth(textBuilder.toString()) < ((this.getWidth() * 2) - ((this.getWidth() * 2) * 0.1))) {
            textBuilder.append(_char);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0)
            selected = isMouseOnButton(mouseX, mouseY);
    }

    @Override
    public String getText() {
        return textBuilder.toString();
    }

    public void setText(String text) {
        textBuilder = new StringBuilder(text);
    }
}
