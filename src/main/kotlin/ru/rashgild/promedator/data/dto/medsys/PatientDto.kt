package ru.rashgild.promedator.data.dto.medsys

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import java.time.LocalDate

@JsonIgnoreProperties(ignoreUnknown = true)
data class PatientDto(

    @JsonProperty("lastname")
    val lastName: String? = null,

    @JsonProperty("firstname")
    val firstName: String? = null,

    @JsonProperty("middlename")
    val middleName: String? = null,

    @JsonSerialize(using = LocalDateSerializer::class)
    @JsonDeserialize(using = LocalDateDeserializer::class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("birthday")
    var birthday: LocalDate? = null,

    @JsonProperty("snils")
    val snils: String? = null
)
