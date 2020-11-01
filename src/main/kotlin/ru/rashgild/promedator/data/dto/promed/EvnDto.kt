package ru.rashgild.promedator.data.dto.promed

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Случай.
 */
data class EvnDto(
    @JsonProperty("Diag_lid") private var diagLid: Long,
    @JsonProperty("Diag_id") private val diagId: Long,
    @JsonProperty("LpuSection_id") private val lpuSectionId: Long? = null,
    @JsonProperty("MedStaffFact_id") private val medStaffFactId: Long? = null,
    @JsonProperty("TreatmentClass_id") private val treatmentClassId: Long,
    @JsonProperty("MedicalCareKind_id") private val medicalCareKindId: Long,
    @JsonProperty("ServiceType_id") private val serviceTypeId: Long,
    @JsonProperty("PayType_id") private val payTypeId: Long,
    @JsonProperty("Evn_setDT") private val evnSetDt: String? = null,
    @JsonProperty("VizitType_id") private val visitTypeId: Long,
    @JsonProperty("ResultDeseaseType_id") private val resultDeseaseTypeId: Long,
    @JsonProperty("EvnPL_NumCard") private val evnPlNumCard: String? = null,
    @JsonProperty("EvnPL_IsFinish") private val isFinish: String? = null,
    @JsonProperty("ResultClass_id") private val resultClassId: Long? = null,
    @JsonProperty("Person_id") private val personId: Long? = null,
    @JsonIgnore private val medsysId: Int? = null
)