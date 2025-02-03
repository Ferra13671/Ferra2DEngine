package com.ferra13671.Ferra2DEngine.Utils.Storage;

import java.util.HashMap;

public class Storage<E> {

    private final HashMap<String, E> storage = new HashMap<>();

    public void addToStorage(String texKey, E element) {
        storage.put(texKey, element);
    }

    public E getFromStorage(String texKey) {
        return storage.get(texKey);
    }

    public void removeFromStorage(String texKey) {
        storage.remove(texKey);
    }

    public boolean containsInStorage(String texKey) {
        return storage.containsKey(texKey);
    }
}
