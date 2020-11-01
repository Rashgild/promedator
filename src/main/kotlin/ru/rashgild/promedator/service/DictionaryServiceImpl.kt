package ru.rashgild.promedator.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.rashgild.promedator.dao.BaseWebClient.Companion.mapToList
import ru.rashgild.promedator.dao.PromedClient
import ru.rashgild.promedator.dao.PromedClient.Companion.validate
import ru.rashgild.promedator.data.dto.promed.dictionary.LpuSectionDictionaryDto
import ru.rashgild.promedator.data.dto.promed.dictionary.MedStaffDictionaryDto

@Service
class DictionaryServiceImpl(
    @Autowired private val promedClient: PromedClient,
) : DictionaryService {

    private val lpuSections: List<LpuSectionDictionaryDto>? = null

    override fun getDictionaryLpuSections(): List<LpuSectionDictionaryDto> {
        return promedClient.getLpuDictionarySection()
            .validate()
            .mapToList(LpuSectionDictionaryDto::class.java)
    }

    override fun getDictionaryMedStaff(lpuSectionId: Long, medPersonalId: Long): List<MedStaffDictionaryDto> {
        return promedClient.getDictionaryMedStaff(lpuSectionId, medPersonalId)
            .validate()
            .mapToList(MedStaffDictionaryDto::class.java)
    }
}