package ru.rashgild.promedator.service

import khttp.responses.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.rashgild.promedator.dao.BaseWebClient.Companion.mapToList
import ru.rashgild.promedator.dao.PromedClient
import ru.rashgild.promedator.dao.PromedClient.Companion.validate
import ru.rashgild.promedator.data.dto.medsys.PatientDto
import ru.rashgild.promedator.data.dto.promed.PersonDto

@Service
class PersonServiceImpl(
    @Autowired private val promedClient: PromedClient
) : PersonService {

    override fun getPersonById(personId: Long?): PersonDto? {
        return promedClient.getPersonById(personId)
            .validate()
            .mapToList(PersonDto::class.java)
            .stream()
            .findFirst()
            .orElse(null)
    }

    override fun getPersonByData(patient: PatientDto): PersonDto? {
        return promedClient.getPersonByData(patient)
            .validate()
            .mapToList(PersonDto::class.java)
            .stream()
            .findFirst()
            .orElse(null)
    }

    override fun getPersonBySnil(snils: String): PersonDto? {
        return promedClient.getPersonBySnils(snils)
            .validate()
            .mapToList(PersonDto::class.java)
            .stream()
            .findFirst()
            .orElse(null)
    }
}
