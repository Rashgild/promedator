package ru.rashgild.promedator.service

import ru.rashgild.promedator.data.dto.medsys.PatientDto
import ru.rashgild.promedator.data.dto.promed.PersonDto

interface PersonService {
    fun getPersonById(personId: Long?): PersonDto?

    fun getPersonByData(patient: PatientDto): PersonDto?

    fun getPersonBySnils(snils: String): PersonDto?
}
