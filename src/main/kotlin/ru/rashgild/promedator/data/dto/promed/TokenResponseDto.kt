package ru.rashgild.promedator.data.dto.promed

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class TokenResponseDto(
    @JsonProperty("sess_id") val sessionId: String,
    @JsonProperty("error_code") var errorCode: String? = null,
)
