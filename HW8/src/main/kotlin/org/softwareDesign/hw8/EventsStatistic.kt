package org.softwareDesign.hw8

interface EventsStatistic {
    fun incEvent(name: String)
    fun getEventStatisticByName(name: String): List<Int>
    fun getAllEventsStatistic(): Map<String, List<Int>>
    fun printStatistic()
}