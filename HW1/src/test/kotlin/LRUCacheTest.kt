import org.junit.Test
import org.softwareDesign.hw1.cache.Cache
import org.softwareDesign.hw1.cache.LRUCache
import kotlin.test.assertEquals
import kotlin.test.assertFails

class LRUCacheTest {
    @Test
    fun emptyCache() {
        LRUCache<Int, Int>(0)
    }

    @Test
    fun negativeCapacity() {
        assertFails { LRUCache<Int, Int>(-1) }
    }

    @Test
    fun testInsertion() {
        val cache = createCache(100, 0)
        for (i in 1..100) {
            assertEquals(i, cache.get(i))
        }
    }

    @Test
    fun testInsertionWithOverflow() {
        val cache = createCache(100,1)
        for (i in 2..101) {
            assertEquals(i, cache.get(i))
        }
        assertFails { cache.get(1) }
    }

    @Test
    fun testInsertionWithOverflow2() {
        val cache = createCache(100,0)
        cache.get(1)
        cache.put(101, 101)
        assertEquals(1, cache.get(1))
        for (i in 3..100) {
            assertEquals(i, cache.get(i))
        }
        assertFails { cache.get(2) }
    }

    @Test
    fun testRewrite() {
        val cache = createCache(100, 0)
        for (i in 1..100) {
            cache.put(i, i + 1)
        }
        cache.put(101, 102)
        for (i in 2..101) {
            assertEquals(i + 1, cache.get(i))
        }
        assertFails { cache.get(1) }
    }

    private fun createCache(capacity : Int, overflow : Int) : Cache<Int, Int> {
        val cache = LRUCache<Int, Int>(capacity)
        for (i in 1..capacity + overflow) {
            cache.put(i, i)
        }
        return cache
    }
}