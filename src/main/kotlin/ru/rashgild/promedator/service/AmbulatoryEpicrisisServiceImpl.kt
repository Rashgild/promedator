package ru.rashgild.promedator.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.rashgild.promedator.dao.BaseWebClient.Companion.mapToList
import ru.rashgild.promedator.dao.BaseWebClient.Companion.validate
import ru.rashgild.promedator.dao.MedsysClient
import ru.rashgild.promedator.data.dto.medsys.MedicalCaseDto

@Service
class AmbulatoryEpicrisisServiceImpl(
    @Autowired private val medicalCaseService: MedicalCaseService
) : AmbulatoryEpicrisisService {

    override fun sync(date: String) {
        val taps = medicalCaseService.getMedicalCase(date)
    }
}