package ru.rashgild.promedator.dao

import com.fasterxml.jackson.databind.ObjectMapper
import khttp.responses.Response
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import ru.rashgild.promedator.data.dto.medos.ScheduleEntryDto

@Component
open class MedosClient(
    private val objectMapper: ObjectMapper,
    @Value("\${medos.url}") private val url: String,
) : AbstractWebClient() {

    companion object {
        private const val SAVE_RECORD_ENDPOINT = "record/makeRecord"
    }

    fun saveRecord(recordDto: ScheduleEntryDto): Response {
        return khttp.post(
            url = url + SAVE_RECORD_ENDPOINT,
            data = objectMapper.writeValueAsString(recordDto)
        )
    }
}
