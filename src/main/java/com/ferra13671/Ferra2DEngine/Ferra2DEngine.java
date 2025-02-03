package com.ferra13671.Ferra2DEngine;

import com.ferra13671.Ferra2DEngine.Event.EventSystem.EventSubscriber;
import com.ferra13671.Ferra2DEngine.Event.EventSystem.EventSystem;
import com.ferra13671.Ferra2DEngine.Event.Events.Input.InputEvent;
import com.ferra13671.Ferra2DEngine.IO.KeyboardUtils;
import com.ferra13671.Ferra2DEngine.IO.Utils.Action;
import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.GLFW.glfwInit;

/**
 * A class containing general data, basic information about the current version of Ferra2DEngine as well as event handler, etc.
 *
 * @author Ferra13671
 */

public class Ferra2DEngine {
    private static final String version = "1.0";
    private static final String nameEngine = "Ferra 2D Engine";
    private static boolean inited = false;

    public static final EventSystem SYS_EVENT_BUS = new EventSystem("SEB", false);

    static {
        SYS_EVENT_BUS.register(new Ferra2DEngine());
    }

    public static void init() {
        if (inited) return;

        GLFWErrorCallback.createPrint(System.err).set();
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        inited = true;
    }

    public static String getEngineFullName() {
        return nameEngine + version;
    }

    public static String getEngineVersion() {
        return version;
    }

    public static String getEngineName() {
        return nameEngine;
    }

    public static boolean isInited() {
        return inited;
    }

    @EventSubscriber
    public void onKey(InputEvent.KeyInputEvent e) {
        if (e.getAction() == Action.PRESS)
            KeyboardUtils.addActiveKey(e.getKey());
        else if (e.getAction() == Action.RELEASE)
            KeyboardUtils.removeActiveKey(e.getKey());
    }
}
