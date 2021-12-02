package org.softwareDesign.hw8.clock

import java.time.Instant

class TestClock(private var now: Instant) : Clock {
    fun setNow(now: Instant) {
        this.now = now
    }

    override fun now(): Instant {
        return now
    }
}