package com.example.modernnotes.domain.util

interface TimeProvider {
    fun currentTimeMillis(): Long
}