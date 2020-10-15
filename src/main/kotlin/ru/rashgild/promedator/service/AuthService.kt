package ru.rashgild.promedator.service

import ru.rashgild.promedator.data.enity.Token

interface AuthService {
    fun auth(): Token
    fun getToken(): Token
    fun getHeader(): Map<String, String>
}
