package ru.rashgild.promedator.data.dto.promed

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class DateTableDto(
    @JsonProperty("TimeTableGraf_id") val timeTableGrafId: Long,
    @JsonProperty("Person_id") val personId: Long
)
