package com.example.modernnotes.core

const val EMPTY_STRING = ""
const val ZERO = 0

fun Int?.orDefault(default: Int = ZERO): Int {
    return this ?: default
}