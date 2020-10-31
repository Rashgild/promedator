package ru.rashgild.promedator.data.dto.promed.dictionary

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class MedStaffDictionaryDto(

    @JsonProperty("MedStaffFact_id")
    var medStaffId: Long? = null,

    @JsonProperty("lpuSectionId")
    val lpuSectionId: Long? = null
)