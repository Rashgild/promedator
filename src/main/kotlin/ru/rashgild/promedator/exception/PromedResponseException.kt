package ru.rashgild.promedator.exception

class PromedResponseException : RuntimeException {
    constructor() : super()

    constructor(message: String) : super(message)
}