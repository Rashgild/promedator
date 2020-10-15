package ru.rashgild.promedator.data.enity

import java.time.LocalDateTime

data class Token(
    val sessionId: String,
    val initDate: LocalDateTime = LocalDateTime.now()
) {
    fun isNotExpire(): Boolean {
       return this.initDate.plusHours(1) >= LocalDateTime.now()
    }
}
