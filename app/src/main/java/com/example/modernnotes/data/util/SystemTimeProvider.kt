package com.example.modernnotes.data.util

import com.example.modernnotes.domain.util.TimeProvider
import javax.inject.Inject

class SystemTimeProvider @Inject constructor() : TimeProvider {

    override fun currentTimeMillis() = System.currentTimeMillis()

}