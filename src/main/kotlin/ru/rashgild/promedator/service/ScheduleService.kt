package ru.rashgild.promedator.service

import ru.rashgild.promedator.data.dto.promed.DateTableDto
import ru.rashgild.promedator.data.dto.promed.TimeTableDto

interface ScheduleService {
    fun getDateList(): List<DateTableDto>
    fun syncronize()
    fun getTimeByDate(dateId: Int): List<TimeTableDto>
}
