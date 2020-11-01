package ru.rashgild.promedator.data.dto.promed

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class EvnVisitDto(
    @JsonProperty("EvnPLBase_id") private var evnPlBaseId: Int? = null,
    @JsonProperty("Diag_id") private val diagId: Long,
    @JsonProperty("LpuSection_id") private val lpuSectionId: Long? = null,
    @JsonProperty("MedStaffFact_id") private val medStaffFactId: Long? = null,
    @JsonProperty("TreatmentClass_id") private val treatmentClassId: Long,
    @JsonProperty("MedicalCareKind_id") private val medicalCareKindId: Long,
    @JsonProperty("ServiceType_id") private val serviceTypeId: Long,
    @JsonProperty("PayType_id") private val payTypeId: Long,
    @JsonProperty("Evn_setDT") private val evnSetDt: String? = null,
    @JsonProperty("VizitType_id") private val visitTypeId: Long,
    @JsonIgnore private val medsysId: Long
)