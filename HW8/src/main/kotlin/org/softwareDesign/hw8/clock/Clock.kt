package org.softwareDesign.hw8.clock

import java.time.Instant

interface Clock {
    fun now(): Instant
}

class NormalClock : Clock {
    override fun now(): Instant {
        return Instant.now()
    }
}