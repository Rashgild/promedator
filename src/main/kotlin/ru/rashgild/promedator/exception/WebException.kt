package ru.rashgild.promedator.exception

import org.springframework.http.HttpStatus

abstract class WebException : RuntimeException {

    constructor() : super()

    constructor(message: String, cause: Throwable, enableSuppression: Boolean, writableStackTrace: Boolean) : super(message, cause, enableSuppression, writableStackTrace)

    constructor(message: String, cause: Throwable) : super(message, cause)

    constructor(message: String) : super(message)

    constructor(cause: Throwable) : super(cause)

    abstract fun getHttpStatus(): HttpStatus
}
