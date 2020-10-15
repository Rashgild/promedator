package ru.rashgild.promedator.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service
import ru.rashgild.promedator.dao.WebClient
import ru.rashgild.promedator.data.dto.promed.TokenResponseDto
import ru.rashgild.promedator.data.enity.Token
import ru.rashgild.promedator.extentions.map
import ru.rashgild.promedator.extentions.validate

@Service
@Scope("singleton")
class AuthServiceImpl(
    @Autowired private val promedClient: WebClient
) : AuthService {

    private lateinit var token: Token

    override fun auth(): Token {
        val tokenDto = promedClient
            .auth()
            .validate()
            .map(TokenResponseDto::class.java)

        token = Token(sessionId = tokenDto.sessionId)
        return token

    }

    override fun getToken(): Token {
        return if (this::token.isInitialized && token.isNotExpire()) {
            token
        } else {
            auth()
        }
    }

    override fun getHeader(): Map<String, String> {
        return mapOf("Cookie" to "PHPSESSID=" + getToken().sessionId)
    }
}
