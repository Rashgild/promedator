package ru.rashgild.promedator.service

import ru.rashgild.promedator.data.dto.promed.WorkPlaceDto

interface WorkPlaceService {
    fun getWorkPlaceByPersonId(personId: Long): List<WorkPlaceDto>?
}