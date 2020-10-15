package ru.rashgild.promedator.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.rashgild.promedator.dao.WebClient
import ru.rashgild.promedator.data.dto.promed.DateTableDto
import ru.rashgild.promedator.data.dto.promed.TimeTableDto
import ru.rashgild.promedator.extentions.mapToList
import ru.rashgild.promedator.extentions.validate
import java.time.LocalDate

@Service
class ScheduleServiceImpl(
    @Autowired private val promedClient: WebClient
) : ScheduleService {

    override fun getDateList(): List<DateTableDto> {
        return promedClient.getDate(LocalDate.now().toString())
            .validate()
            .mapToList(DateTableDto::class.java)
    }

    override fun getTimeByDate(dateId: Int): List<TimeTableDto> {
        return promedClient.getTimeByDateId(dateId)
            .validate()
            .mapToList(TimeTableDto::class.java)
    }

    override fun syncronize() {
    }
}
