package ru.rashgild.promedator.service

import ru.rashgild.promedator.data.dto.promed.DateTableDto
import ru.rashgild.promedator.data.dto.promed.TimeTableDto

interface ScheduleService {
    fun getDateList(date: String): List<DateTableDto>
    fun synchronize(date: String)
    fun getTimeByDate(dateId: Long?): List<TimeTableDto>
}
