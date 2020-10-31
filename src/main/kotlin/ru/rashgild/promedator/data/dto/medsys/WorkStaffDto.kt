package ru.rashgild.promedator.data.dto.medsys

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class WorkStaffDto(
    @JsonProperty("doctorWf")
    val workFunction: String? = null,

    @JsonProperty("doctorLastname")
    val lastName: String? = null,

    @JsonProperty("doctorFirstname")
    val firstName: String? = null,

    @JsonProperty("doctorMiddlename")
    val middleName: String? = null,

    @JsonProperty("doctorSnils")
    val snils: String,

    @JsonProperty("promedCode_workstaff")
    val workStaffId: Int? = null,

    ) {
    fun getFio(): String {
        return lastName + firstName + middleName
    }
}
