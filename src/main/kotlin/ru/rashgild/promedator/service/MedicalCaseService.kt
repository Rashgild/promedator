package ru.rashgild.promedator.service

import ru.rashgild.promedator.data.dto.medsys.MedicalCaseDto
import ru.rashgild.promedator.data.dto.promed.EvnRequestDto
import ru.rashgild.promedator.data.enity.MedCase

interface MedicalCaseService {
    fun getMedicalCase(date: String): List<MedicalCaseDto>
    fun medCaseDtoToMedCase(medCaseDto: MedicalCaseDto): MedCase
    fun mapMedCaseToTapRequestDto(listMedCase: List<MedCase>): MutableList<EvnRequestDto?>
}