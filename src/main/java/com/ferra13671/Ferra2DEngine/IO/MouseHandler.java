package com.ferra13671.Ferra2DEngine.IO;

import com.ferra13671.Ferra2DEngine.IO.Utils.Action;
import com.ferra13671.Ferra2DEngine.IO.Utils.Key;

import java.util.ArrayList;

/**
 * A class for handling mouse button presses and releases.
 *
 * @author Ferra13671
 */

public class MouseHandler {
    protected double mouseX;
    protected double mouseY;
    protected double prevMouseX;
    protected double prevMouseY;
    ArrayList<Key> buttons = new ArrayList<>();



    public void activateInput(int button, Action action) {
        if (action == Action.PRESS) {
            boolean needAdd = true;

            for (Key key: buttons) {
                if (key.key == button) {
                    needAdd = false;
                    break;
                }
            }

            if (needAdd)
                buttons.add(new Key(button));

            buttonPressed(button);
        } else {
            buttons.removeIf(k -> k.key == button);

            buttonReleased(button);
        }
    }

    public void updateMousePos(double mouseX, double mouseY) {
        prevMouseX = this.mouseX;
        prevMouseY = this.mouseY;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    public boolean isButtonPressed(int button) {
        for (Key key : buttons) {
            if (key.key == button)
                return true;
        }
        return false;
    }


    public void buttonPressed(int button) {

    }

    public void buttonReleased(int button) {

    }

    public double getMouseX() {
        return mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }

    public double getPrevMouseX() {
        return prevMouseX;
    }

    public double getPrevMouseY() {
        return prevMouseY;
    }
}
