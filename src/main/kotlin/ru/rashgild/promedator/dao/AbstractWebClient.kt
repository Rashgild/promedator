package ru.rashgild.promedator.dao

import khttp.responses.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.rashgild.promedator.service.AuthService

@Component
abstract class AbstractWebClient {

    @Autowired
    internal lateinit var authService: AuthService

    fun get(url: String, params: Map<String, String>): Response {
        return khttp.get(
            url = url,
            params = params
        )
    }
}






