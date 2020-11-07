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
data class WorkPlaceDto(

    @JsonProperty("MedWorker_id") var medWorkerId: Long,
    @JsonProperty("MedPersonal_id") var medPersonalId: Long,
    @JsonProperty("MedStaffFact_id") var medStaffFactId: Long,
    @JsonProperty("Lpu_id") val lpuId: Long,
    @JsonProperty("LpuSection_id") val lpuSectionId: Long,

    @JsonSerialize(using = LocalDateSerializer::class)
    @JsonDeserialize(using = LocalDateDeserializer::class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("BeginDate")
    val beginDate: LocalDate? = null,

    @JsonSerialize(using = LocalDateSerializer::class)
    @JsonDeserialize(using = LocalDateDeserializer::class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("EndDate")
    val endDate: LocalDate? = null
)
