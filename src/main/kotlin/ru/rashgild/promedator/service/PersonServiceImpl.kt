package ru.rashgild.promedator.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.rashgild.promedator.dao.PromedClient
import ru.rashgild.promedator.data.dto.promed.PersonDto
import ru.rashgild.promedator.extentions.promedDtoToList
import ru.rashgild.promedator.extentions.validate

@Service
class PersonServiceImpl(
    @Autowired private val promedClient: PromedClient
) : PersonService {

    override fun getPersonById(personId: Long?): PersonDto? {
        return promedClient.getPersonById(personId)
            .validate()
            .promedDtoToList(PersonDto::class.java)
            .stream()
            .findFirst()
            .orElse(null)
    }
}
