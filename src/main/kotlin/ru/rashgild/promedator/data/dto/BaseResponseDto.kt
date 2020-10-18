package ru.rashgild.promedator.data.dto

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class BaseResponseDto<E> (

    @JsonProperty("error_code")
    val errorCode: Int? = 0,

    @JsonProperty("someName")
    @JsonAlias("tap", "data")
    val data: Collection<E>,
)
