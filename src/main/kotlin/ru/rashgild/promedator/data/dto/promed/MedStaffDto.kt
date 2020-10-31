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
data class MedStaffDto(
    private var id: Int? = null,

    private val medStaffId: Int? = null,

    @JsonProperty("MedPersonal_id")
    private val medPersonalId: Int? = null,

    @JsonProperty("Staff_id")
    private val staffId: Int? = null,

    @JsonProperty("BeginDate")
    @JsonSerialize(using = LocalDateSerializer::class)
    @JsonDeserialize(using = LocalDateDeserializer::class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private val beginDate: LocalDate? = null,

    @JsonProperty("EndDate")
    @JsonSerialize(using = LocalDateSerializer::class)
    @JsonDeserialize(using = LocalDateDeserializer::class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private val endDate: LocalDate? = null,

    @JsonProperty("LpuSection_id")
    private val lpuSectionId: Int? = null,

    @JsonProperty("Lpu_id")
    private val lpuId: Int? = null,
)