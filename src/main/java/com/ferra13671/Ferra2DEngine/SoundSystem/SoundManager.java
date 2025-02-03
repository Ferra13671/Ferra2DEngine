package com.ferra13671.Ferra2DEngine.SoundSystem;

import com.ferra13671.Ferra2DEngine.Logger.Logger;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;

import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.openal.ALC10.alcMakeContextCurrent;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_decode_filename;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryStack.stackPop;
import static org.lwjgl.system.libc.LibCStdlib.free;

/**
 * Sound manager needed to work with sound files.
 * The manager uses openAL to play sounds, so initialization of the manager takes place during window creation.
 *
 * @author Ferra13671
 */

public class SoundManager {
    private static boolean inited = false;
    private static final Logger logger = new Logger(SoundManager.class);

    private static long audioContext;
    private static long audioDevice;


    /**
     * Complete initialization of the sound system.
     * If the sound system succeeds in initialization, true is returned, otherwise false.
     *
     * @return   Whether the initialization was successful or not.
     */
    public static boolean init() {
        if (inited) {
            logger.logInfo("SoundSystem is already initialized");
            return true;
        }

        //Initializing Audio Context
        String defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
        audioDevice = alcOpenDevice(defaultDeviceName);

        int[] attributes = {0};
        audioContext = alcCreateContext(audioDevice, attributes);
        alcMakeContextCurrent(audioContext);

        ALCCapabilities alcCapabilities = ALC.createCapabilities(audioDevice);
        ALCapabilities alCapabilities = AL.createCapabilities(alcCapabilities);

        if (!alCapabilities.OpenAL10) {
            logger.logError("Audio Library not supported!");
            return false;
        }
        inited = true;
        return true;
        /////////////////
    }


    /**
     * Destroys the sound system. Used only when destroying a window.
     */
    public static void destroy() {
        alcDestroyContext(audioContext);
        alcCloseDevice(audioDevice);
    }


    /**
     * Converts and prepares the .ogg file for further playback.
     *
     * @param path   File path.
     * @param loop   Whether the sound is looped or not.
     * @return   bufferId and sourceId needed for further work with sound.
     */
    public static int[] getOggMusic(String path, boolean loop) {
        int[] musicData = {0,0}; // bufferId , sourceId

        // Allocate space to store the return information from stb
        stackPush();
        IntBuffer channelsBuffer = stackMallocInt(1);
        stackPush();
        IntBuffer sampleRateBuffer = stackMallocInt(1);

        ShortBuffer rawAudioBuffer = stb_vorbis_decode_filename(path, channelsBuffer, sampleRateBuffer);

        if (rawAudioBuffer == null) {
            logger.logError("Could not load music '" + path + "'");
            stackPop();
            stackPop();
            return null;
        }

        // Retrieve the extra information that was stored in the buffers by stb
        int channels = channelsBuffer.get();
        int sampleRate = sampleRateBuffer.get();
        // Free
        stackPop();
        stackPop();

        // Find the correct openAL format
        int format = -1;
        if (channels == 1) {
            format = AL_FORMAT_MONO16;
        } else if (channels == 2) {
            format = AL_FORMAT_STEREO16;
        }

        musicData[0] = alGenBuffers();
        alBufferData(musicData[0], format, rawAudioBuffer, sampleRate);

        // Generate the source
        musicData[1] = alGenSources();
        alSourcei(musicData[1], AL_BUFFER, musicData[0]);
        alSourcei(musicData[1], AL_LOOPING, loop ? 1 : 0);
        alSourcei(musicData[1], AL_POSITION, 0);
        // Sound Volume
        alSourcef(musicData[1], AL_GAIN, 1f);

        // Free stb raw audio buffer
        free(rawAudioBuffer);

        return musicData;
    }
}
