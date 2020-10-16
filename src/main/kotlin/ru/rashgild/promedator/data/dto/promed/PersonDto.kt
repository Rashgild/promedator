package ru.rashgild.promedator.data.dto.promed

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import java.time.LocalDate

@JsonIgnoreProperties(ignoreUnknown = true)
data class PersonDto(

    @JsonProperty("Person_id")
    val personId: Long = 0,

    @JsonProperty("PersonSurName_SurName")
    val surName: String? = null,

    @JsonProperty("PersonFirName_FirName")
    val firstName: String? = null,

    @JsonProperty("PersonSecName_SecName")
    val secName: String? = null,

    @JsonProperty("PersonBirthDay_BirthDay")
    @JsonSerialize(using = LocalDateSerializer::class)
    @JsonDeserialize(using = LocalDateDeserializer::class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    val birthday: LocalDate? = null,

    @JsonProperty("Person_Sex_id")
    val sexId: Long? = null,

    @JsonProperty("PersonPhone_Phone")
    val phone: String? = null,

    @JsonProperty("PersonSnils_Snils")
    val snils: String? = null
)
