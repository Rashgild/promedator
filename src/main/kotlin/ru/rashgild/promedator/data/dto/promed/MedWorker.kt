package ru.rashgild.promedator.data.dto.promed

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class MedWorker(
    private var id: Int? = null,
    private val medWorkerId: Int? = null
)