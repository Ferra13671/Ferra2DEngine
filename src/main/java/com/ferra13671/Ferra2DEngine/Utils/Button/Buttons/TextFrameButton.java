package com.ferra13671.Ferra2DEngine.Utils.Button.Buttons;


import com.ferra13671.Ferra2DEngine.IO.KeyboardUtils;
import com.ferra13671.Ferra2DEngine.Render.RenderHelper;
import com.ferra13671.Ferra2DEngine.Render.TextRenderer.TextWithSize;
import com.ferra13671.Ferra2DEngine.Utils.Button.Button;

import java.awt.*;

public class TextFrameButton extends Button {
    private final StringBuilder textBuilder = new StringBuilder();

    private boolean selected = false;


    public TextFrameButton(int id, int centerX, int centerY, int width, int height, int textSize) {
        super(id,centerX,centerY, width, height, new TextWithSize("", textSize));
    }


    @Override
    public void renderButton() {
        RenderHelper.drawRect(getCenterX() - getWidth(), getCenterY() - getHeight(), getCenterX() + getWidth(), getCenterY() + getHeight(), rectColor);

        RenderHelper.drawText(textBuilder.toString(), getCenterX() - getWidth() + 3, getCenterY() - (text.size / 2f), Color.white.hashCode());
    }

    @Override
    public void keyTyped(int key) {
        super.keyTyped(key);

        if (!selected) return;

        if (key == KeyboardUtils.KEY_BACKSPACE) {
            if (textBuilder.length() > 0) {
                textBuilder.deleteCharAt(textBuilder.length() - 1);
            }
        }
    }

    @Override
    public void charTyped(char _char) {
        super.charTyped(_char);

        if (!selected) return;

        if (RenderHelper.textRenderer.getStringWidth(textBuilder.toString(), text.size) < ((getWidth() * 2) - ((getWidth() * 2) * 0.1))) {
            textBuilder.append(_char);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        this.selected = isMouseOnButton(mouseX, mouseY);
    }

    @Override
    public TextWithSize getText() {
        return new TextWithSize(textBuilder.toString(), text.size);
    }
}
