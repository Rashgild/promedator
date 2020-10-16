package ru.rashgild.promedator.dao

import com.fasterxml.jackson.databind.ObjectMapper
import khttp.responses.Response
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import ru.rashgild.promedator.data.dto.medsys.ScheduleEntryDto

@Component
open class MedsysClient(
    private val objectMapper: ObjectMapper,
    @Value("\${medsys.url}") private val url: String,
) : AbstractWebClient() {

    companion object {
        private const val SAVE_RECORD_ENDPOINT = "record/makeRecord"
        private const val POLYCLINC_MED_CASE_ENDPOINT ="promed/getPolyclinicCase"
        private const val MED_CASE_STATUS_ENDPOINT ="promed/setEvnTap"
    }

    fun saveRecord(recordDto: ScheduleEntryDto): Response {
        return khttp.post(
            url = url + SAVE_RECORD_ENDPOINT,
            data = objectMapper.writeValueAsString(recordDto)
        )
    }

    fun getMedicalCase(date: String): Response {
       return khttp.get(
            url = url + POLYCLINC_MED_CASE_ENDPOINT,
            params = mapOf(
                "dateTo" to date,
                "isUpload" to "true"
            )
        )
    }

    fun sendMedicalCaseStatus(medicalCaseId: Long, evnId: Long):Response {
        return khttp.post(
            url = url + MED_CASE_STATUS_ENDPOINT,
            params = mapOf(
                "medcase_id" to medicalCaseId.toString(),
                "tap_id" to evnId.toString()
            )
        )
    }
}
