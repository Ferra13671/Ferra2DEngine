package com.ferra13671.Ferra2DEngine.Utils.Button.Buttons;

import com.ferra13671.Ferra2DEngine.IO.KeyboardUtils;
import com.ferra13671.Ferra2DEngine.Render.RenderHelper;
import com.ferra13671.Ferra2DEngine.Render.TextRenderer.TextWithSize;
import com.ferra13671.Ferra2DEngine.Utils.Button.Button;
import com.ferra13671.Ferra2DEngine.Utils.ListUtils;

import java.awt.*;
import java.util.Set;

public class NumberFrameButton extends Button {
    private final StringBuilder textBuilder = new StringBuilder();
    private boolean isDouble = false;

    private boolean selected = false;

    private final int textSize;

    private final Set<String> keys = ListUtils.newHashSet("1","2","3","4","5","6","7","8","9","0");


    public NumberFrameButton(int id, int centerX, int centerY, int width, int height, int textSize) {
        super(id,centerX,centerY, width, height, new TextWithSize("", 0));
        this.textSize = textSize;
    }


    @Override
    public void renderButton() {
        RenderHelper.drawRect(getCenterX() - getWidth(), getCenterY() - getHeight(), getCenterX() + getWidth(), getCenterY() + getHeight(), rectColor);

        RenderHelper.drawText(textBuilder.toString(), getCenterX() - getWidth() + 3, getCenterY() - (textSize / 2f), Color.white.hashCode());
    }


    @Override
    public void keyTyped(int key) {
        super.keyTyped(key);

        if (!selected) return;

        if (key == KeyboardUtils.KEY_BACKSPACE) {
            if (textBuilder.length() > 0) {
                if (Character.toString(textBuilder.charAt(textBuilder.length() - 1)).equals("."))
                    isDouble = false;
                textBuilder.deleteCharAt(textBuilder.length() - 1);
            }
        }
    }

    @Override
    public void charTyped(char _char) {
        super.charTyped(_char);

        if (!selected) return;

        String symbol = Character.toString(_char);

        if (symbol.equals("-")) {
            if (textBuilder.length() == 0) {
                textBuilder.append(_char);
            }
            return;
        }

        if (keys.contains(symbol)) {
            if (RenderHelper.textRenderer.getStringWidth(getText()) < ((getWidth() * 2) - ((getWidth() * 2) * 0.1))) {
                textBuilder.append(_char);
            }
            return;
        }

        if (symbol.equals(".")) {
            if (!isDouble && RenderHelper.textRenderer.getStringWidth(getText()) < ((getWidth() * 2) - ((getWidth() * 2) * 0.1)) && textBuilder.length() != 0) {
                textBuilder.append(_char);
                isDouble = true;
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        selected = isMouseOnButton(mouseX, mouseY);
    }

    @Override
    public TextWithSize getText() {
        return new TextWithSize(textBuilder.toString(), textSize);
    }

    public double getNumber() {
        if (textBuilder.length() > 0) {
            if (textBuilder.charAt(textBuilder.length() - 1) == '.') {
                textBuilder.append("0");
            }
        }
        if (textBuilder.length() == 0) {
            return 0;
        }

        return Double.parseDouble(textBuilder.toString());
    }
}
