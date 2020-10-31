package ru.rashgild.promedator.service

import ru.rashgild.promedator.data.dto.promed.dictionary.LpuSectionDictionaryDto
import ru.rashgild.promedator.data.dto.promed.dictionary.MedStaffDictionaryDto

interface DictionaryService {
    fun getDictionaryLpuSections(): List<LpuSectionDictionaryDto>
    fun getDictionaryMedStaff(lpuSectionId: Long, medPersonalId: Long): List<MedStaffDictionaryDto>
}