package com.ferra13671.Ferra2DEngine.IO;

import com.ferra13671.Ferra2DEngine.Event.Events.Input.CharInputEvent;
import com.ferra13671.Ferra2DEngine.Event.Events.Input.InputEvent;
import com.ferra13671.Ferra2DEngine.Ferra2DEngine;
import com.ferra13671.Ferra2DEngine.IO.Utils.Action;
import com.ferra13671.Ferra2DEngine.IO.Utils.Key;
import com.ferra13671.Ferra2DEngine.window.Window;
import org.lwjgl.glfw.GLFW;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A class for handling key presses and releases.
 *
 * @author Ferra13671
 */

public class KeyboardHandler {
    private final Window window;

    private final CopyOnWriteArrayList<Key> activeKeys = new CopyOnWriteArrayList<>();

    public KeyboardHandler(Window window) {
        this.window = window;
    }



    public void activateKeyInput(int key, int action) {
        if (action == Action.PRESS.state) {
            if (!activeKeys.contains(key))
                activeKeys.add(new Key(key));

            keyPressed(key);

            Ferra2DEngine.SYS_EVENT_BUS.activate(new InputEvent.KeyInputEvent(key, Action.PRESS));
        } else if (action == Action.RELEASE.state) {
            activeKeys.removeIf(k -> k.key == key);

            keyReleased(key);

            Ferra2DEngine.SYS_EVENT_BUS.activate(new InputEvent.KeyInputEvent(key, Action.RELEASE));
        } else {
            keyJammed(key);

            Ferra2DEngine.SYS_EVENT_BUS.activate(new InputEvent.KeyInputEvent(key, Action.JAMMED));
        }
    }

    public void activateCharInput(char _char, int modifiers) {
        Ferra2DEngine.SYS_EVENT_BUS.activate(new CharInputEvent(_char));
    }

    public void keyPressed(int key) {

    }

    public void keyReleased(int key) {
        if (key == GLFW.GLFW_KEY_F11) {
            window.setWindowFullScreen(!window.isWindowFullScreen());
        }
    }

    public void keyJammed(int key) {

    }

    public List<Key> getActiveKeys() {
        return activeKeys;
    }
}
