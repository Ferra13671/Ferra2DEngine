package com.ferra13671.Ferra2DEngine.Render;

import com.ferra13671.Ferra2DEngine.Utils.Annotations.Experimental;
import com.ferra13671.Ferra2DEngine.window.Window;

/**
 * A class created only to pass control of what to render in a window to code outside the Window class.
 *
 * @author Ferra13671
 */
@Experimental("It will probably be broken up into 1-3 events.")
public abstract class RenderManager {
    private final Window window;



    public RenderManager(Window window) {
        this.window = window;
    }

    /**
     * Method needed only for initialization before rendering. Here you can (sometimes need to) put methods with texture loading or something similar.
     */
    public abstract void init();


    /**
     * A method that is only responsible for rendering.
     * <b>Do not put in it methods related to texture loading or something similar, because it will greatly affect fps.</b>
     */
    public abstract void render();


    public Window getWindow() {
        return this.window;
    }
}
