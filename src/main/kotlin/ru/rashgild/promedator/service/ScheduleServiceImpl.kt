package ru.rashgild.promedator.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.rashgild.promedator.dao.MedosClient
import ru.rashgild.promedator.dao.PromedClient
import ru.rashgild.promedator.data.dto.medos.ScheduleEntryDto
import ru.rashgild.promedator.data.dto.promed.DateTableDto
import ru.rashgild.promedator.data.dto.promed.PersonDto
import ru.rashgild.promedator.data.dto.promed.TimeTableDto
import ru.rashgild.promedator.extentions.mapToList
import ru.rashgild.promedator.extentions.validate

@Service
class ScheduleServiceImpl(
    @Value("\$medos.token") private val medosToken: String,
    @Autowired private val promedClient: PromedClient,
    @Autowired private val medosClient: MedosClient,
    @Autowired private val personService: PersonService
) : ScheduleService {

    override fun getDateList(date: String): List<DateTableDto> {
        return promedClient.getDate(date)
            .validate()
            .mapToList(DateTableDto::class.java)
    }

    override fun getTimeByDate(dateId: Long?): List<TimeTableDto> {
        return promedClient.getTimeByDateId(dateId!!)
            .validate()
            .mapToList(TimeTableDto::class.java)
    }

    override fun synchronize(date: String) {
        val listDates = getDateList(date)
        listDates
            .parallelStream()
            .forEach { dateTableDto: DateTableDto? ->
                val person = personService.getPersonById(dateTableDto?.personId)
                if (person != null) {
                    val listTimes = getTimeByDate(dateTableDto?.timeTableGrafId)
                    listTimes.forEach { timeTableDto ->
                        val entry: ScheduleEntryDto = createMedosModel(person, timeTableDto)
                        medosClient.saveRecord(entry)
                    }
                }
            }
    }

    private fun createMedosModel(person: PersonDto, timeTable: TimeTableDto): ScheduleEntryDto {
        return ScheduleEntryDto(
            lastName = person.surName,
            firstName = person.firstName,
            middleName = person.secName,
            birthday = person.birthday,
            phone = person.phone,
            recordCalendarDate = timeTable.dateTime?.toLocalDate(),
            recordCalendarTime = timeTable.dateTime?.toLocalTime(),
            doctorPromedId = timeTable.medStaffFactId,
            reserveCode = "PROMED",
            recordType = "PROMED",
            token = medosToken
        )
    }
}
