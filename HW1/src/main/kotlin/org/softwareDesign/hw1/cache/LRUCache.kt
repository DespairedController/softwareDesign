package org.softwareDesign.hw1.cache

class LRUCache<Key, Value>(private val capacity: Int) : Cache<Key, Value> {
    private val map = hashMapOf<Key, DoubleLinkedList.Just<Key, Value>>()
    private val list = DoubleLinkedList<Key, Value>()

    init {
        assert(capacity >= 0)
    }

    override fun get(k: Key): Value {
        if (k in map) {
            val node = map[k]!!
            list.moveNodeToHead(node)
            return node.value
        } else {
            throw NoSuchElementException("There is no element $k in cache")
        }
    }

    override fun put(k: Key, v: Value) {
        if (k in map) {
            val node = map[k]!!
            list.moveNodeToHead(node)
            node.value = v
            return
        }
        if (list.size() >= capacity) {
            map.remove(list.dropLast())
        }
        val node = list.add(k, v)
        map[k] = node
    }
}

private class DoubleLinkedList<Key, Value> {
    private val head = Nothing<Key, Value>()
    private var tail: Just<Key, Value>? = null
    private var listSize = 0

    fun add(k: Key, v: Value): Just<Key, Value> {
        generalAssertions()
        val prevNext = head.next
        val newNode = Just(head, k, v)
        newNode.next = prevNext
        newNode.previous = head
        head.next = newNode
        if (tail == null) {
            tail = newNode
        } else {
            prevNext!!.previous = newNode
        }
        listSize++
        return newNode
    }

    fun moveNodeToHead(node: Just<Key, Value>) {
        generalAssertions()
        assert(node.previous != null)
        assert(tail != null)
        assert(head.next != null)

        if (listSize == 1 && node == tail) {
            return
        }

        val prev = node.previous!!
        if (tail == node) {
            prev.next = null
            tail = prev as Just
        } else {
            val next = node.next!!
            prev.next = next
            next.previous = prev
        }
        val headNext = head.next!!
        head.next = node
        node.previous = head
        node.next = headNext
        headNext.previous = node
    }

    fun dropLast(): Key {
        generalAssertions()
        assert(tail != null)
        val key = tail!!.key
        val prevTail = tail!!.previous!!
        prevTail.next = null
        tail = if (prevTail == head) {
            null
        } else {
            prevTail as Just
        }
        listSize--
        return key
    }

    fun size() = listSize

    private fun generalAssertions() {
        assert(head.previous == null)
        assert(tail == null || tail!!.next == null)
        assert(tail == null || tail!!.previous != null)
    }

    open class Node<K, V>(var previous: Node<K, V>?) {
        var next: Node<K, V>? = null
    }

    class Just<K, V>(previous: Node<K, V>, val key: K, var value: V) : Node<K, V>(previous)

    class Nothing<K, V> : Node<K, V>(null)

}