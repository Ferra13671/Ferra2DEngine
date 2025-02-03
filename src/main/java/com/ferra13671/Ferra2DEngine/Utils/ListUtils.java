package com.ferra13671.Ferra2DEngine.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Utility for creating various lists.
 *
 * @author Ferra13671
 */

public final class ListUtils {

    /**
     * Creates a new <b>HashSet</b> that already contains the items specified in the arguments.
     *
     * @param elements   The elements that will lie in the <b>HashSet</b>.
     * @return   A new <b>HashSet</b> with the elements lying inside.
     */
    @SafeVarargs
    public static <E> HashSet<E> newHashSet(E... elements) {
        HashSet<E> set = new HashSet<>(elements.length);
        Collections.addAll(set, elements);
        return set;
    }


    /**
     * Creates a new <b>CopyOnWriteArraySet</b> that already contains the items specified in the arguments.
     *
     * @param elements   The elements that will lie in the <b>CopyOnWriteArraySet</b>.
     * @return   A new <b>CopyOnWriteArraySet</b> with the elements lying inside.
     */
    @SafeVarargs
    public static <E> CopyOnWriteArraySet<E> newCopyOnWriteArraySet(E... elements) {
        CopyOnWriteArraySet<E> set = new CopyOnWriteArraySet<>();
        Collections.addAll(set, elements);
        return set;
    }


    /**
     * Creates a new <b>ArrayList</b> that already contains the items specified in the arguments.
     *
     * @param elements   The elements that will lie in the <b>ArrayList</b>.
     * @return   A new <b>ArrayList</b> with the elements lying inside.
     */
    @SafeVarargs
    public static <E> ArrayList<E> newArrayList(E... elements) {
        ArrayList<E> list = new ArrayList<>(elements.length);
        Collections.addAll(list, elements);
        return list;
    }


    /**
     * Creates a new <b>CopyOnWriteArrayList</b> that already contains the items specified in the arguments.
     *
     * @param elements   The elements that will lie in the <b>CopyOnWriteArrayList</b>.
     * @return   A new <b>CopyOnWriteArrayList</b> with the elements lying inside.
     */
    @SafeVarargs
    public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList(E... elements) {
        CopyOnWriteArrayList<E> list = new CopyOnWriteArrayList<>();
        Collections.addAll(list, elements);
        return list;
    }
}
