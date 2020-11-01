package ru.rashgild.promedator.data.enity

data class Visit(
    val personId: Long? = null,
    val medStaffId: Long? = null,
    val lpuSectionId: Long? = null,
    val resultDiseaseTypeId: Long,
    val serviceTypeId: Long,
    val visitDate: String,
    val numCard: Long,
    val diagId: Long,
    val diagLid: Long,
    val treatmentClassId: Long,
    val resultClassId: Long? = null,
    val payTypeId: Long,
    val medicalCareKindId: Long,
    val isFinish: Boolean? = null,
    val firstVisit: Boolean,
    val medsysId: Long,
    val dairy: String,
    var visitTypeId: Long
)
