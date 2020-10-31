package ru.rashgild.promedator.data.dto.promed.dictionary

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class LpuSectionDictionaryDto(

    @JsonProperty("LpuSection_id")
    var lpuSectionId: Long,

    @JsonProperty("LpuUnit_id")
    val lpuUnitId: Long? = null,

    @JsonProperty("LpuUnit_Name")
    val lpuUnitName: String? = null,

    @JsonProperty("LpuSection_Name")
    val lpuSectionName: String? = null,
)
