package org.softwareDesign.hw1.cache

interface Cache<Key, Value> {
    /**
     *
     */
    fun get(k: Key): Value

    /**
     *
     */
    fun put(k: Key, v:Value)
}