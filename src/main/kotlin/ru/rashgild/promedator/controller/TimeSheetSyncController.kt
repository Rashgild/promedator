package ru.rashgild.promedator.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import ru.rashgild.promedator.service.ScheduleService
import java.time.DayOfWeek
import java.time.LocalDate

@Controller
@RequestMapping("/schedule")
class TimeSheetSyncController(
    @Autowired private val scheduleService: ScheduleService
) {

    @GetMapping(value = ["/sync-by-date"], produces = ["application/json"])
    fun syncByDate(@RequestParam(value = "date", required = false) date: String?): ResponseEntity<Any> {

        val syncDate = if (date.isNullOrEmpty()) {
            when (LocalDate.now().dayOfWeek) {
                DayOfWeek.SATURDAY -> {
                    LocalDate.now().plusDays(2).toString()
                }
                DayOfWeek.SUNDAY -> {
                    LocalDate.now().plusDays(1).toString()
                }
                else -> LocalDate.now().toString()
            }
        } else {
            date
        }

        scheduleService.synchronize(syncDate)
        return ResponseEntity(HttpStatus.OK)
    }
}
