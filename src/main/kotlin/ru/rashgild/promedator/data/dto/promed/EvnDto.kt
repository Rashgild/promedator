package ru.rashgild.promedator.data.dto.promed

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Случай.
 */
data class EvnDto(
    @JsonProperty("Diag_lid")
    private var diagLid: Int? = null,
    @JsonProperty("Diag_id")
    private val diagId: Int? = null,
    @JsonProperty("LpuSection_id")
    private val lpuSectionId: Long? = null,
    @JsonProperty("MedStaffFact_id")
    private val medStaffFactId: Long? = null,
    @JsonProperty("TreatmentClass_id")
    private val treatmentClassId: Int? = null,
    @JsonProperty("MedicalCareKind_id")
    private val medicalCareKindId: Int? = null,
    @JsonProperty("ServiceType_id")
    private val serviceTypeId: Int? = null,
    @JsonProperty("PayType_id")
    private val payTypeId: Int? = null,
    @JsonProperty("Evn_setDT")
    private val evnSetDt: String? = null,
    @JsonProperty("VizitType_id")
    private val visitTypeId: Int? = null,
    @JsonProperty("ResultDeseaseType_id")
    private val resultDeseaseTypeId: Int? = null,
    @JsonProperty("EvnPL_NumCard")
    private val evnPlNumCard: String? = null,
    @JsonProperty("EvnPL_IsFinish")
    private val isFinish: String? = null,
    @JsonProperty("ResultClass_id")
    private val resultClassId: Int? = null,
    @JsonProperty("Person_id")
    private val personId: Int? = null,
    @JsonIgnore
    private val medosId: Int? = null
)