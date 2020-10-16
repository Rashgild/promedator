package ru.rashgild.promedator.data.dto.medsys

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer
import java.time.LocalDate
import java.time.LocalTime

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class ScheduleEntryDto(
    @JsonProperty("lastname")
    var lastName: String? = null,

    @JsonProperty("firstname")
    val firstName: String? = null,

    @JsonProperty("middlename")
    val middleName: String? = null,

    @JsonProperty("reserveCode")
    val reserveCode: String? = null,

    @JsonProperty("recordType")
    val recordType: String? = null,

    @JsonProperty("phone")
    val phone: String? = null,

    @JsonProperty("birthday")
    @JsonSerialize(using = LocalDateSerializer::class)
    @JsonDeserialize(using = LocalDateDeserializer::class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    val birthday: LocalDate? = null,

    @JsonProperty("recordCalendarDate")
    @JsonSerialize(using = LocalDateSerializer::class)
    @JsonDeserialize(using = LocalDateDeserializer::class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    val recordCalendarDate: LocalDate? = null,

    @JsonProperty("recordCalendarTime")
    @JsonSerialize(using = LocalTimeSerializer::class)
    @JsonDeserialize(using = LocalTimeDeserializer::class)
    @JsonFormat(pattern = "HH:mm")
    val recordCalendarTime: LocalTime? = null,

    @JsonProperty("doctorPromedId")
    val doctorPromedId: Long? = null,

    @JsonProperty("token")
    val token: String? = null
)
