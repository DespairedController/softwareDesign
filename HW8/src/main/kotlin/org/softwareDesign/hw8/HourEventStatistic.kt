package org.softwareDesign.hw8

import org.softwareDesign.hw8.clock.Clock
import java.time.Instant

class HourEventStatistic(private val clock: Clock) : EventsStatistic {
    private val map = mutableMapOf<String, EventData>()

    override fun incEvent(name: String) {
        map.getOrPut(name) { EventData(clock.now()) }.incCounter()
    }

    override fun getEventStatisticByName(name: String): List<Int> {
        return map.getOrPut(name) { EventData(clock.now()) }.getStatistics()
    }

    override fun getAllEventsStatistic(): Map<String, List<Int>> {
        return map.mapValues { it.value.getStatistics() }
    }

    override fun printStatistic() {
        map.forEach { (eventName, statistics) ->
            println("Statistics for $eventName:")
            for (number in statistics.getStatistics()) {
                print("$number ")
            }
            print("\n\n")
        }
    }

    private inner class EventData(private var hourStartTime: Instant) {
        private val counter = (1..60).map { 0 }.toMutableList()

        private fun synchronize() {
            val now = clock.now()
            val passedMinutes = (now.epochSecond - hourStartTime.epochSecond) / 60
            repeat(passedMinutes.toInt()) {
                counter.removeLast()
                counter.add(0, 0)
            }
            hourStartTime = now
        }

        fun incCounter() {
            synchronize()
            counter[0]++
        }

        fun getStatistics(): List<Int> {
            synchronize()
            return counter.toList()
        }
    }
}