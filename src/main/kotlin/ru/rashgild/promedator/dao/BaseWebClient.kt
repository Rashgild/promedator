package ru.rashgild.promedator.dao

import com.fasterxml.jackson.databind.ObjectMapper
import khttp.responses.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.rashgild.promedator.data.dto.BaseResponseDto
import ru.rashgild.promedator.exception.BadRequestWebException
import ru.rashgild.promedator.extentions.map
import ru.rashgild.promedator.extentions.mapToList
import ru.rashgild.promedator.service.AuthService

@Component
open class BaseWebClient {

    @Autowired
    internal lateinit var authService: AuthService

    internal val objectMapper: ObjectMapper = ObjectMapper()

    companion object {
        val objectMapper: ObjectMapper = ObjectMapper()

        fun Response.validate(): Response {
            if (this.statusCode == 200) {
                return this
            } else {
                throw BadRequestWebException("not valid \"${this.statusCode}\"") //TODO()
            }
        }

        fun <T> Response.map(targetClass: Class<T>?): T {
            return objectMapper.map(this.text, targetClass)
        }

        fun <T> Response.mapToList(targetClass: Class<T>?): List<T> {
            return objectMapper.mapToList(
                map(BaseResponseDto::class.java).data,
                targetClass
            )
        }
    }
}
