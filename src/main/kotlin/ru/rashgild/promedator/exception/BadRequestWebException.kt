package ru.rashgild.promedator.exception

import org.springframework.http.HttpStatus

class BadRequestWebException : WebException {

    constructor() : super()

    constructor(msg: String) : super(msg)

    constructor(message: String, cause: Throwable) : super(message, cause)

    companion object {
        private const val serialVersionUID = 927587973057858443L
    }

    override fun getHttpStatus(): HttpStatus {
        return HttpStatus.BAD_REQUEST
    }
}
