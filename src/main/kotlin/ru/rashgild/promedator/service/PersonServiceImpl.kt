package ru.rashgild.promedator.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.rashgild.promedator.dao.BaseWebClient.Companion.mapToList
import ru.rashgild.promedator.dao.PromedClient
import ru.rashgild.promedator.dao.PromedClient.Companion.validate
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
}
