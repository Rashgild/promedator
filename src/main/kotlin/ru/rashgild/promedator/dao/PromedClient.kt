package ru.rashgild.promedator.dao

import khttp.responses.Response
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
open class PromedClient(
    @Value("\${promed.url}") private val url: String,
    @Value("\${promed.login}") private val login: String,
    @Value("\${promed.password}") private val password: String,
    @Value("\${promed.lpu.id}") private val lpuId: String
) : AbstractWebClient() {

    companion object {
        private const val PERSON_ENDPOINT = "api/Person"
        private const val DATE_TABLE_ENDPOINT = "api/TimeTableGraf/TimeTableGrafbyMO"
        private const val TIME_TABLE_ENDPOINT = "api/TimeTableGraf/TimeTableGrafById"
        private const val LOGIN_ENDPOINT = "api/user/login"
        private const val LOGOUT_ENDPOINT = "api/user/logout"
    }

    fun auth(): Response {
        return khttp.get(
            url = url + LOGIN_ENDPOINT,
            params = mapOf(
                "login" to login,
                "password" to password
            )
        )
    }

    fun logout(): Response {
        return khttp.get(
            url = url + LOGOUT_ENDPOINT
        )
    }

    fun getDate(date: String): Response {
        return khttp.get(
            url = url + DATE_TABLE_ENDPOINT,
            params = mapOf(
                "TimeTableGraf_beg" to date,
                "TimeTableGraf_end" to date,
                "Lpu_id" to lpuId
            ),
            headers = authService.getHeader()
        )
    }

    fun getTimeByDateId(dateId: Long): Response {
        return khttp.get(
            url = url + TIME_TABLE_ENDPOINT,
            params = mapOf(
                "TimeTableGraf_id" to dateId.toString()
            ),
            headers = authService.getHeader()
        )
    }

    fun getPersonById(personId: Long?): Response {
        return khttp.get(
            url = url + PERSON_ENDPOINT,
            params = mapOf(
                "Person_id" to personId.toString()
            ),
            headers = authService.getHeader()
        )
    }
}
