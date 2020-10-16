package ru.rashgild.promedator.extentions

import com.fasterxml.jackson.databind.ObjectMapper
import khttp.responses.Response
import ru.rashgild.promedator.data.dto.promed.ResponseDataDto
import ru.rashgild.promedator.data.dto.promed.ResponseErrorDto
import ru.rashgild.promedator.exception.PromedResponseException

private val objectMapper: ObjectMapper = ObjectMapper()

fun Response.validate(): Response {
    if (this.statusCode == 200) {
        val error = this.map(ResponseErrorDto::class.java)
        if (!error.message.isNullOrEmpty()) {
            throw PromedResponseException(error.message)
        } else {
            return this
        }
    } else {
        throw PromedResponseException(this.statusCode.toString())
    }
}

fun <T> Response.map(targetClass: Class<T>?): T {
    return objectMapper.map(this.text, targetClass)
}

fun <T> Response.promedDtoToList(targetClass: Class<T>?): List<T> {
    return objectMapper.mapToList(
        map(ResponseDataDto::class.java).data,
        targetClass
    )
}
