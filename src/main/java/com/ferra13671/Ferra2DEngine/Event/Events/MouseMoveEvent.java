package com.ferra13671.Ferra2DEngine.Event.Events;

import com.ferra13671.Ferra2DEngine.Event.EventSystem.Event;

public class MouseMoveEvent extends Event {
    private final double mouseX;
    private final double mouseY;

    public MouseMoveEvent(double mouseX, double mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    public double getMouseX() {
        return mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }
}
