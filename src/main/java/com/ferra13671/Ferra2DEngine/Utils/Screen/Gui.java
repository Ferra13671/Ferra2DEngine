package com.ferra13671.Ferra2DEngine.Utils.Screen;

import com.ferra13671.Ferra2DEngine.Utils.Button.Button;
import com.ferra13671.Ferra2DEngine.window.Window;

import java.util.concurrent.CopyOnWriteArrayList;

public class Gui {
    public final Window window;
    public final GuiManager manager;
    public final CopyOnWriteArrayList<Button> buttons = new CopyOnWriteArrayList<>();
    public Button activeButton = Button.of(Integer.MIN_VALUE, -100, -100, 1, 1, "nullButton");
    public Gui parent;

    public Gui(Window window, GuiManager manager) {
        this.window = window;
        this.manager = manager;
    }

    public void init() {}

    public void onOpen() {}

    public void onClose() {}

    public void render(int mouseX, int mouseY) {
        for (Button button : buttons) {
            if (!button.isHided()) {
                button.updateButton(mouseX, mouseY);

                button.renderButton();
            }
        }
    }

    public void onMouseClicked(double mouseX, double mouseY, int mouseButton) {
        activeButton = Button.of(Integer.MIN_VALUE, -100, -100, 1, 1, "nullButton");

        for (Button button : buttons) {
            if (!button.isHided()) {
                button.mouseClicked((int) mouseX, (int) mouseY, mouseButton);
                if (button.isMouseOnButton((int) mouseX, (int) mouseY)) {
                    if (mouseButton == 0) {
                        activeButton = button;
                        button.clickAction((int) mouseX, (int) mouseY, mouseButton);
                    }
                }
            }
        }
    }

    public void onMouseReleased(double mouseX, double mouseY, int mouseButton) {
        for (Button button : buttons) {
            button.mouseReleased((int) mouseX, (int) mouseY, mouseButton);
        }
    }

    public void onKeyTyped(int keyCode) {
        for (Button button : buttons) {
            if (!button.isHided())
                button.keyTyped(keyCode);
        }
    }

    public void onCharTyped(char _char) {
        for (Button button : buttons) {
            if (!button.isHided())
                button.charTyped(_char);
        }
    }

    public Button getButtonFromId(int id) {
        for (Button button : buttons) {
            if (button.getId() == id)
                return button;
        }
        return null;
    }
}
