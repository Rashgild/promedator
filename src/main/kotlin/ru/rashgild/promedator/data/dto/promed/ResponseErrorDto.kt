package ru.rashgild.promedator.data.dto.promed

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ResponseErrorDto(
    @JsonProperty("error_msg")
    val message: String?,
    @JsonProperty("error_code")
    val errorCode: Int?
)