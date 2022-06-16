package com.example.abe.jpbc.util.collection;

import com.example.abe.jpbc.util.collection.LatchHashMap;

/**
 * @author Angelo De Caro (jpbclib@gmail.com)
 * @since 2.0.0
 */
public class FlagMap<K> {

    protected LatchHashMap<K, Boolean> flags;

    public FlagMap() {
        this.flags = new LatchHashMap<K, Boolean>();
    }

    public void get(K key) {
        flags.get(key);
    }

    public void set(K key) {
        flags.put(key, true);
    }

}
