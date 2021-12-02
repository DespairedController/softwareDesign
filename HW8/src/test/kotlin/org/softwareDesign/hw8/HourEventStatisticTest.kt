package org.softwareDesign.hw8

import org.junit.Test
import org.softwareDesign.hw8.clock.TestClock
import java.time.Instant
import kotlin.test.assertEquals

private const val now = "2021-11-02T18:00:00Z"

class HourEventStatisticTest {

    private val clock = TestClock(Instant.parse(now))

    private fun createStatistic(): EventsStatistic {
        return HourEventStatistic(clock)
    }

    @Test
    fun testEmptyStatistic() {
        assertEquals(0, createStatistic().getAllEventsStatistic().size)
    }

    @Test
    fun testEmptyEvent() {
        val stat = createStatistic()
        assertEquals((1..60).map { 0 }, stat.getEventStatisticByName("someName"))
    }

    @Test
    fun testSingleIncEvent() {
        val stat = createStatistic()
        stat.incEvent("someName")
        val list = generateExpectedList(mapOf(1 to 1))

        assertEquals(list, stat.getEventStatisticByName("someName"))
    }

    @Test
    fun testShiftTimeSingleEvent() {
        val stat = createStatistic()
        stat.incEvent("someName")
        updateClock(17, 15)
        stat.incEvent("someName")
        val list = generateExpectedList(mapOf(1 to 1, 18 to 1))

        assertEquals(list, stat.getEventStatisticByName("someName"))
    }

    @Test
    fun testOneEventInStatistic() {
        val stat = createStatistic()
        stat.incEvent("someName")

        assertEquals(
            mapOf("someName" to generateExpectedList(mapOf(1 to 1))),
            stat.getAllEventsStatistic()
        )
    }

    @Test
    fun testTwoElementsInStatistic() {
        val stat = createStatistic()
        stat.incEvent("someName1")
        stat.incEvent("someName2")

        val list = generateExpectedList(mapOf(1 to 1))
        assertEquals(
            mapOf("someName1" to list, "someName2" to list),
            stat.getAllEventsStatistic()
        )
    }

    @Test
    fun testIncTwiceInMinute() {
        val stat = createStatistic()
        stat.incEvent("someName")
        stat.incEvent("someName")

        assertEquals(
            generateExpectedList(mapOf(1 to 2)),
            stat.getEventStatisticByName("someName")
        )
    }

    private fun updateClock(minutes: Int, seconds: Int) {
        clock.setNow(Instant.ofEpochSecond(clock.now().epochSecond + 60 * minutes + seconds))
    }

    private fun generateExpectedList(map: Map<Int, Int>) = (1..60).map {
        map[it] ?: 0
    }

}