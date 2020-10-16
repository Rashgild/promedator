package ru.rashgild.promedator.service

import ru.rashgild.promedator.data.dto.promed.PersonDto

interface PersonService {
    fun getPersonById(personId: Long?): PersonDto?
}
