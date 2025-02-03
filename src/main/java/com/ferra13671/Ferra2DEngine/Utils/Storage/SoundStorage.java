package com.ferra13671.Ferra2DEngine.Utils.Storage;

import com.ferra13671.Ferra2DEngine.SoundSystem.Sound;

/**
 * A class for conveniently storing and retrieving <b>sounds</b> using a text key.
 *
 * @author Ferra13671
 */

public class SoundStorage {
    private static final Storage<Sound> storage = new Storage<>();

    /**
     * Adds a <b>sound</b> to the storage with the specified text key.
     *
     * @param texKey   A text key that will be used to retrieve the <b>sound</b>.
     * @param sound   A <b>sound</b> that will be written under the desired textual key.
     */
    public static void addSoundToStorage(String texKey, Sound sound) {
        storage.addToStorage(texKey, sound);
    }

    /**
     * Retrieves a <b>sound</b> from storage using a textual key.
     *
     * @param texKey   The textual key for which the <b>sound</b> was assigned.
     * @return   A <b>sound</b> that has been assigned to a text key.
     */
    public static Sound getSound(String texKey) {
        return storage.getFromStorage(texKey);
    }

    /**
     * Removes a <b>sound</b> from storage using the text key.
     *
     * @param texKey   The textual key to which the <b>sound</b> has been assigned.
     */
    public static void removeSoundFromStorage(String texKey) {
        storage.getFromStorage(texKey).delete();
        storage.removeFromStorage(texKey);
    }

    /**
     * Returns whether there is a <b>sound</b> assigned to the desired textual key in the storage.
     *
     * @param texKey   Text key to be checked.
     * @return   Whether there is a <b>sound</b> in the storage assigned to this textual key.
     */
    public static boolean contains(String texKey) {
        return storage.containsInStorage(texKey);
    }
}
