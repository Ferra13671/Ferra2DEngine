package com.ferra13671.Ferra2DEngine.window;

import com.ferra13671.Ferra2DEngine.Event.Events.Input.InputEvent;
import com.ferra13671.Ferra2DEngine.Event.Events.MouseMoveEvent;
import com.ferra13671.Ferra2DEngine.Event.Events.WindowFocusEvent;
import com.ferra13671.Ferra2DEngine.Event.Events.WindowResizeEvent;
import com.ferra13671.Ferra2DEngine.Ferra2DEngine;
import com.ferra13671.Ferra2DEngine.IO.Utils.Action;
import com.ferra13671.Ferra2DEngine.IO.KeyboardHandler;
import com.ferra13671.Ferra2DEngine.IO.MouseHandler;
import com.ferra13671.Ferra2DEngine.Render.RenderHelper;
import com.ferra13671.Ferra2DEngine.Render.RenderManager;
import com.ferra13671.Ferra2DEngine.SoundSystem.SoundManager;
import com.ferra13671.Ferra2DEngine.Utils.FPSUtil;
import com.ferra13671.Ferra2DEngine.Utils.IconUtil;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.awt.*;
import java.io.Closeable;
import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * A class designed to simplify the creation and interaction with a window.
 * There are many different functions that do not need to be created manually.
 * <p>
 * Creating more than 1 window is not supported in the code(although you can freely create multiple windows).
 * Creating multiple windows is guaranteed to cause problems.
 *
 * @author Ferra13671
 */

public final class Window implements Closeable {

    public final long windowId;

    public KeyboardHandler keyboardHandler = new KeyboardHandler(this);
    public MouseHandler mouseHandler = new MouseHandler();
    public RenderManager renderManager = new RenderManager(this) {
        @Override
        public void init() {
        }
        @Override
        public void render() {
        }
    };

    private int width;
    private int height;
    private int oldWidth;
    private int oldHeight;

    private int xPos;
    private int yPos;
    private int oldXPos;
    private int oldYPos;

    public GLFWImage.Buffer currentIcon;

    public FPSUtil fpsUtil;

    private String windowTitle = "";
    private boolean fullScreen = false;
    private boolean looped = false;
    private boolean closed = false;


    /**
     * Creates a new GLFW window.
     * <p>
     * <b>Note that the {@link #createWindow(int, int, boolean)} method must be used to make the window fully ready for use.</b>
     */
    public Window() {
        if (!Ferra2DEngine.isInited()) Ferra2DEngine.init();

        windowId = glfwCreateWindow(1, 1, windowTitle, NULL, NULL);
    }


    /**
     * Creates a basic window ready for use.
     *
     * @param windowWidth    Length of the new window in pixels.
     * @param windowHeight    Height of the new window in pixels.
     */
    public void createWindow(int windowWidth , int windowHeight, boolean resizable) {
        //Preparing GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_AUTO_ICONIFY, GLFW_TRUE);
        glfwWindowHint(GLFW_FOCUS_ON_SHOW, GLFW_TRUE);
        glfwWindowHint(GLFW_DOUBLEBUFFER, GLFW_TRUE);
        //glfwWindowHint ( GLFW_TRANSPARENT_FRAMEBUFFER , GLFW_TRUE );


        //Creating a new window
        setWindowSize(windowWidth, windowHeight);
        this.width = windowWidth;
        this.height = windowHeight;
        if ( windowId == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(windowId, (window, key, scancode, action, mods) -> {
            if (window == windowId) {
                keyboardHandler.activateKeyInput(key, action);
            }
        });
        glfwSetCharModsCallback(windowId, (windowx, codePoint, modifiers) -> {
            if (windowx == windowId) {
                if (Character.charCount(codePoint) == 1) {
                    keyboardHandler.activateCharInput((char) codePoint, modifiers);
                } else {
                    char[] chars = Character.toChars(codePoint);

                    for (char c : chars) {
                        keyboardHandler.activateCharInput(c, modifiers);
                    }
                }
            }
        });

        glfwSetMouseButtonCallback(windowId, new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                if (window == windowId) {
                    mouseHandler.activateInput(button, action == GLFW_PRESS ? Action.PRESS : Action.RELEASE);
                    Ferra2DEngine.SYS_EVENT_BUS.activate(new InputEvent.MouseInputEvent(button, action == GLFW_PRESS ? Action.PRESS : Action.RELEASE));
                }
            }
        });

        glfwSetCursorPosCallback(windowId, new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double mouseX, double mouseY) {
                if (window == windowId) {
                    Ferra2DEngine.SYS_EVENT_BUS.activate(new MouseMoveEvent(mouseX, mouseY));
                    mouseHandler.updateMousePos(mouseX, mouseY);
                }
            }
        });

        glfwSetWindowFocusCallback(windowId, new GLFWWindowFocusCallback() {
            @Override
            public void invoke(long window, boolean focused) {
                if (window == windowId) {
                    Ferra2DEngine.SYS_EVENT_BUS.activate(new WindowFocusEvent(focused));
                }
            }
        });

        glfwSetFramebufferSizeCallback(windowId, new GLFWFramebufferSizeCallback() {
            @Override
            public void invoke(long window, int width_, int height_) {
                if (window == windowId) {
                    if (width_ != 0 && height_ != 0) {
                        glLoadIdentity();
                        width = width_;
                        height = height_;
                        reloadMatrix();
                        glViewport(0, 0, width_, height_);

                        Ferra2DEngine.SYS_EVENT_BUS.activate(new WindowResizeEvent());
                    }
                }
            }
        });

        glfwSetWindowPosCallback(windowId, new GLFWWindowPosCallback() {
            @Override
            public void invoke(long window, int xpos, int ypos) {
                if (window == windowId) {
                    xPos = xpos;
                    yPos = ypos;
                }
            }
        });


        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(windowId, pWidth, pHeight);

            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            glfwSetWindowPos(
                    windowId,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        }

        glfwMakeContextCurrent(windowId);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(windowId);

        //Initializing the sound system
        SoundManager.init();

        fpsUtil = new FPSUtil();

        GL.createCapabilities();
        reloadMatrix();

        RenderHelper.init();
    }

    /**
     * Starts the window render and refresh cycle.
     * <p>
     * <b>
     * Note that after starting the loop itself cannot stop unless the window is closed,
     * so if another action is to be performed after calling this method,
     * it will be performed after the window is closed.
     * </b>
     */
    public void startLooping() {
        renderManager.init();
        looped = true;

        while (!isShouldCloseWindow()) {

            fpsUtil.updateFPS();

            if (!isLooped())
                break;

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            renderManager.render();

            glfwSwapBuffers(windowId); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
        close();
    }

    public void reloadMatrix() {
        glViewport(0,0, this.width, this.height);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, this.width, this.height, 0, 0, 1);
    }

    public boolean isShouldCloseWindow() {
        return glfwWindowShouldClose(windowId) || closed;
    }

    /**
     * Closes the window and stops the program completely.
     */
    @Override
    public void close() {
        SoundManager.destroy();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(windowId);
        glfwDestroyWindow(windowId);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
        System.exit(0);
    }

    /**
     * Marks the window as "closed". This method differs from {@link #close()} in that the loop will always fully complete when closed.
     */
    public void closeSafely() {
        closed = true;
    }


    /**
     * Sets the entered text as the window name.
     * Only English language is supported.
     */
    public void setWindowTitle(Object title) {
        glfwSetWindowTitle(windowId, title.toString());
        this.windowTitle = title.toString();
    }


    /**
     * Enables or disables the window's full-screen mode.
     *
     * @param in    Value specifying whether full screen mode will be enabled(true) or disabled(false).
     */
    public void setWindowFullScreen(boolean in) {
        if (in) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            oldWidth = width;
            oldHeight = height;

            oldXPos = xPos;
            oldYPos = yPos;

            glfwSetWindowMonitor(windowId, glfwGetPrimaryMonitor(), 0, 0, screenSize.width, screenSize.height, 250);

            fullScreen = true;
        } else {

            glfwSetWindowMonitor(windowId, NULL, 0, 0, oldWidth, oldHeight, 250);

            setWindowPos(oldXPos, oldYPos);

            fullScreen = false;
        }
    }


    /**
     * Sets the image as the icon for this window.
     *
     * @param path  The name or path to the desired picture.
     */
    public void setWindowIcon(String path) {
        final IconUtil icon = IconUtil.loadImage(path);
        GLFWImage image = GLFWImage.malloc();
        currentIcon = null;
        currentIcon = GLFWImage.malloc(1);
        image.set(icon.getWidth(), icon.getHeight(), icon.getImage());
        currentIcon.put(0, image);
        glfwSetWindowIcon(windowId, currentIcon);
    }


    /**
     * Sets the upper left corner of the window (excluding the frame) to the specified coordinates.
     *
     * @param x length position (in pixels)
     * @param y height position (in pixels)
     */
    public void setWindowPos(int x, int y) {
        glfwSetWindowPos(windowId, x, y);
    }


    /**
     * @return Whether full screen mode is enabled or not
     */
    public boolean isWindowFullScreen() {
        return fullScreen;
    }


    /**
     * Sets the window size according to the selected ready-made configuration.
     *
     * @param size   A pre-made version of the window size that conforms to monitor resolution standards.
     */
    public void setWindowPreparedSize(WindowResolution size) {
        glfwSetWindowSize(windowId, size.getWidth(), size.getHeight());

    }


    /**
     * Sets the window size to the specified width and height.
     *
     * @param width   Desired window width (in pixels).
     * @param height   Desired window height (in pixels).
     */
    public void setWindowSize(int width, int height) {
        if (width < 0 || height < 0) {
            return;
        }
        glfwSetWindowSize(windowId, width, height);
    }


    /**
     * Draw attention by highlighting this window (if supported by the system).
     */
    public void attentionRequest() {
        glfwRequestWindowAttention(windowId);
    }


    /**
     * Changes the transparency of the window to the selected one.
     *
     * @param alpha   Real number from 0 to 1.
     */
    public void setWindowOpacity(float alpha) {
        glfwSetWindowOpacity(windowId, alpha);
    }

    /**
     * Returns whether the window is currently in a loop.
     */
    public boolean isLooped() {
        return looped;
    }

    /**
     * Returns the current window width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the current window height.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the current window title.
     */
    public String getWindowTitle() {
        return windowTitle;
    }
}
