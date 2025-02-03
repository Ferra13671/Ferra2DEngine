package com.ferra13671.Ferra2DEngine.Event.Events;

import com.ferra13671.Ferra2DEngine.Event.EventSystem.Event;

/**
 * @author Ferra13671
 */

public class WindowFocusEvent extends Event {
    private final boolean focused;

    public WindowFocusEvent(boolean focused) {
        this.focused = focused;
    }

    public boolean getFocused() {
        return this.focused;
    }
}
