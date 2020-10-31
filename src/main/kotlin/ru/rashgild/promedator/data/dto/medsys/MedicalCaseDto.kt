package ru.rashgild.promedator.data.dto.medsys

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class MedicalCaseDto (

    @JsonProperty("patient")
    val patient: PatientDto,

    @JsonProperty("EvnPL_NumCard")
    val numCard: Int? = null,

    @JsonProperty("EvnPL_IsFinish")
    val isFinish: Int? = null,

    @JsonProperty("Diag_lid")
    val diagLid: Int? = null,

    @JsonProperty("ResultClass_id")
    val resultClass: Int? = null,

    @JsonProperty("visits")
    val visits: List<VisitDto>? = null
)
