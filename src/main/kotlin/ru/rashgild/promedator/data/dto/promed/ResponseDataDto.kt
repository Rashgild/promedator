package ru.rashgild.promedator.data.dto.promed

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ResponseDataDto<E> (
    @JsonProperty("error_code")
    val errorCode: Int,
    @JsonProperty("data")
    val data: Collection<E>,
)
