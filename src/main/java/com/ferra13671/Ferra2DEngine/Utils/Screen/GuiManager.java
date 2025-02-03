package com.ferra13671.Ferra2DEngine.Utils.Screen;

import com.ferra13671.Ferra2DEngine.Event.EventSystem.EventSubscriber;
import com.ferra13671.Ferra2DEngine.Event.Events.Input.CharInputEvent;
import com.ferra13671.Ferra2DEngine.Event.Events.Input.InputEvent;
import com.ferra13671.Ferra2DEngine.Event.Events.WindowResizeEvent;
import com.ferra13671.Ferra2DEngine.Ferra2DEngine;
import com.ferra13671.Ferra2DEngine.IO.Utils.Action;
import com.ferra13671.Ferra2DEngine.window.Window;

/**
 * GuiManager is a manager that helps you conveniently render and interact with various menus.
 * To make the manager work, you need to call the {@link #register()} method, and to render the current menu you need to call the {@link #render()} method.
 *
 * @author Ferra13671
 */

public class GuiManager {
    private final Window window;

    public Gui currentGui;

    public GuiManager(Window window) {
        this.window = window;
    }


    public void register() {
        Ferra2DEngine.SYS_EVENT_BUS.register(this);
    }

    public void render() {
        if (currentGui != null)
            currentGui.render((int) window.mouseHandler.getMouseX(), (int) window.mouseHandler.getMouseY());
    }

    public void setGui(Gui gui) {
        if (currentGui != null) {
            currentGui.onClose();
        }
        currentGui = gui;
        if (gui != null) {
            currentGui.init();
            currentGui.onOpen();
        }
    }

    @EventSubscriber
    public void onCLick(InputEvent.MouseInputEvent e) {
        if (e.getAction() == Action.PRESS) {
            currentGui.onMouseClicked(window.mouseHandler.getMouseX(), window.mouseHandler.getMouseY(), e.getButton());
        }
        if (e.getAction() == Action.RELEASE) {
            currentGui.onMouseReleased(window.mouseHandler.getMouseX(), window.mouseHandler.getMouseY(), e.getButton());
        }
    }

    @EventSubscriber
    public void onWindowResize(WindowResizeEvent e) {
        if (currentGui != null) {
            currentGui.init();
        }
    }

    @EventSubscriber
    public void onKey(InputEvent.KeyInputEvent e) {
        if (currentGui != null && e.getAction() == Action.PRESS) {
            currentGui.onKeyTyped(e.getKey());
        }
    }

    @EventSubscriber
    public void onChar(CharInputEvent e) {
        if (currentGui != null) {
            currentGui.onCharTyped(e.getChar());
        }
    }
}
