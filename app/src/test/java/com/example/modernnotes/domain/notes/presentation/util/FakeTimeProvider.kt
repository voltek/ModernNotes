package com.example.modernnotes.domain.notes.presentation.util

import com.example.modernnotes.domain.util.TimeProvider

class FakeTimeProvider(private val fixedTime: Long = 1000L) : TimeProvider {

    override fun currentTimeMillis() = fixedTime

}