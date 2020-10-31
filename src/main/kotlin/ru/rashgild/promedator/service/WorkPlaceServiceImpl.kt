package ru.rashgild.promedator.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.rashgild.promedator.dao.BaseWebClient.Companion.mapToList
import ru.rashgild.promedator.dao.PromedClient
import ru.rashgild.promedator.dao.PromedClient.Companion.validate
import ru.rashgild.promedator.data.dto.promed.WorkPlaceDto

@Service
class WorkPlaceServiceImpl(
    @Autowired private val promedClient: PromedClient
): WorkPlaceService {

    override fun getWorkPlaceByPersonId(personId: Long): List<WorkPlaceDto>? {
        return promedClient
            .getWorkPlaceByPerson(personId.toString())
            .validate()
            .mapToList(WorkPlaceDto::class.java)
    }
}