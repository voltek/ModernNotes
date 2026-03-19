package com.example.modernnotes.core

const val EMPTY_STRING = ""
const val ZERO = 0

const val APPLICATION_JSON = "application/json"

fun Int?.orDefault(default: Int = ZERO): Int {
    return this ?: default
}