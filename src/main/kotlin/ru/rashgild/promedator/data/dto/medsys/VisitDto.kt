package ru.rashgild.promedator.data.dto.medsys

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import ru.rashgild.promedator.data.dto.promed.dictionary.MedStaffDictionaryDto
import java.time.LocalDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
data class VisitDto(
    @JsonProperty("MedStaffFact_id") val medStaffId: Long? = null,
    @JsonProperty("MedosId") val medsysId: Long,
    @JsonProperty("ServiceType_id") val serviceTypeId: Long,
    @JsonProperty("DeseaseType_id") val diseaseTypeId: Long,
    @JsonProperty("Diag_lid") val diagId: Int? = null,
    @JsonProperty("Diag_code") val diagCode: String? = null,
    @JsonProperty("LpuSection_id") val lpuSectionId: Long? = null,
    @JsonProperty("diary") val diary: String,
    @JsonProperty("MedicalCareKind_id") val medicalCareKindId: Int? = null,
    @JsonProperty("Mes_id") val mesId: Long? = null,
    @JsonProperty("PayType_id") val payType: String? = null,

    @JsonSerialize(using = LocalDateTimeSerializer::class)
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("Evn_setDT")
    val visitDate: LocalDateTime,

    @JsonProperty("VizitType_id") val visitTypeId: Long,
    @JsonProperty("firstVisit") val firstVisit: Boolean,
    @JsonProperty("WorkStaffInfo") val workStaff: WorkStaffDto,

    @JsonProperty("medStaffDictionary")
    @JsonIgnore
    var medStaffDictionary: MedStaffDictionaryDto? = null
) {
    companion object {
        fun getDiary(visitDto: VisitDto): String {
            return "<data><autoname47>" + visitDto.diary
                .replace("\\", "/")
                .replace(">", "&gt;")
                .replace("<", "&lt;") +
                    "</autoname47></data>"
        }
    }
}
