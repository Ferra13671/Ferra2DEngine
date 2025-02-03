package com.ferra13671.Ferra2DEngine.Utils.Button;


import com.ferra13671.Ferra2DEngine.Render.RenderHelper;

import java.awt.*;

public class Button {

    private final int id;

    public double centerX;
    public double centerY;

    private int width;
    private int height;

    public String text;

    public boolean hovered;

    public boolean outline = true;
    public boolean hided = false;



    public Button(int id, int x, int y, int width, int height, String text) {
        this.id = id;

        this.centerX = x;
        this.centerY = y;

        this.width = width;
        this.height = height;

        this.text = text;
    }

    public void updateButton(int mouseX, int mouseY) {
        if (hided) return;
        this.hovered = isMouseOnButton(mouseX, mouseY);
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {}

    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {}


    public Color rectColor = new Color(0, 0, 0, 150);

    public Color whiteColor = new Color(255,255,255, 178);
    public Color alphaColor = new Color(255,255,255,0);

    public void renderButton() {
        if (hided) return;
        if (!this.hovered) {
            RenderHelper.drawRect(getCenterX() - this.width, getCenterY() - this.height, getCenterX() + this.width, getCenterY() + this.height, rectColor);
            if (outline)
                RenderHelper.drawOutlineRect(getCenterX() - this.width, getCenterY() - this.height, getCenterX() + this.width, getCenterY() + this.height, 1, Color.white);
        } else {
            RenderHelper.drawRect(getCenterX() - this.width - 1, getCenterY() - this.height - 1, getCenterX() + this.width + 1, getCenterY() + this.height + 1, rectColor);

            RenderHelper.drawHorizontalGradientRect((int)(getCenterX() - (this.width * 0.8)), getCenterY() + this.height - 4, getCenterX(), getCenterY() + this.height - 2, alphaColor, whiteColor);
            RenderHelper.drawHorizontalGradientRect(getCenterX(), getCenterY() + this.height - 4, (int)(getCenterX() + (this.width * 0.8)), getCenterY() + this.height - 2, whiteColor, alphaColor);
            if (outline)
                RenderHelper.drawOutlineRect(getCenterX() - this.width - 1, getCenterY() - this.height - 1, getCenterX() + this.width + 1, getCenterY() + this.height + 1, 1, Color.white);
        }
        RenderHelper.fontRenderer.draw(getText(), (getCenterX() - (RenderHelper.fontRenderer.getStringWidth(getText()) / 2)), (getCenterY() - (RenderHelper.fontRenderer.getStringHeight(getText()) / 2f)), -1, false);
    }



    public boolean isMouseOnButton(int mouseX, int mouseY) {
        return getCenterX() - width <= mouseX && mouseX <= getCenterX() + width && getCenterY() - height <= mouseY && mouseY <= getCenterY() + height;
    }

    public void keyTyped(int key) {}

    public void charTyped(char _char) {}


    public int getId() {
        return this.id;
    }

    public int getCenterX() {
        return (int) centerX;
    }

    public int getCenterY() {
        return (int) centerY;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public String getText() {
        return text;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setText(String text) {
        this.text = text;
    }
}
