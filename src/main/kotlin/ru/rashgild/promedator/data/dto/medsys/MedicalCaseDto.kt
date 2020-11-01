package ru.rashgild.promedator.data.dto.medsys

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class MedicalCaseDto(
    @JsonProperty("patient") val patient: PatientDto,
    @JsonProperty("EvnPL_NumCard") val numCard: Long,
    @JsonProperty("EvnPL_IsFinish") val isFinish: Int? = null,
    @JsonProperty("Diag_lid") val diagLid: Long,
    @JsonProperty("ResultClass_id") val resultClass: Long,
    @JsonProperty("visits") val visits: List<VisitDto>? = null
)
