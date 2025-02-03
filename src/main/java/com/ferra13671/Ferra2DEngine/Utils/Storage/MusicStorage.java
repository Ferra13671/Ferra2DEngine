package com.ferra13671.Ferra2DEngine.Utils.Storage;

import com.ferra13671.Ferra2DEngine.SoundSystem.Music;

/**
 * A class for conveniently storing and retrieving <b>musics</b> using a text key.
 *
 * @author Ferra13671
 */

public class MusicStorage {
    private static final Storage<Music> storage = new Storage<>();

    /**
     * Adds a <b>music</b> to the storage with the specified text key.
     *
     * @param texKey   A text key that will be used to retrieve the <b>music</b>.
     * @param music   A <b>music</b> that will be written under the desired textual key.
     */
    public static void addMusicToStorage(String texKey, Music music) {
        storage.addToStorage(texKey, music);
    }

    /**
     * Retrieves a <b>music</b> from storage using a textual key.
     *
     * @param texKey   The textual key for which the <b>music</b> was assigned.
     * @return   A <b>music</b> that has been assigned to a text key.
     */
    public static Music getMusic(String texKey) {
        return storage.getFromStorage(texKey);
    }

    /**
     * Removes a <b>music</b> from storage using the text key.
     *
     * @param texKey   The textual key to which the <b>music</b> has been assigned.
     */
    public static void removeMusicFromStorage(String texKey) {
        storage.getFromStorage(texKey).delete();
        storage.removeFromStorage(texKey);
    }

    /**
     * Returns whether there is a <b>music</b> assigned to the desired textual key in the storage.
     *
     * @param texKey   Text key to be checked.
     * @return   Whether there is a <b>music</b> in the storage assigned to this textual key.
     */
    public static boolean contains(String texKey) {
        return storage.containsInStorage(texKey);
    }
}
