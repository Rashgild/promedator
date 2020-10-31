package ru.rashgild.promedator.service

import ru.rashgild.promedator.data.dto.medsys.MedicalCaseDto

interface MedicalCaseService {
    fun getMedicalCase(date: String): List<MedicalCaseDto>
}