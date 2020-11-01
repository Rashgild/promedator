package ru.rashgild.promedator.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.rashgild.promedator.data.enity.MedCase

@Service
class AmbulatoryEpicrisisServiceImpl(
    @Autowired private val medicalCaseService: MedicalCaseService
) : AmbulatoryEpicrisisService {

    override fun sync(date: String) {
        val listMedicalCaseDto = medicalCaseService.getMedicalCase(date)

        val listMedCases: MutableList<MedCase> = ArrayList()
        listMedicalCaseDto.forEach { medicalCaseDto ->
            listMedCases.add(medicalCaseService.medCaseDtoToMedCase(medicalCaseDto))
        }

        val evnList = medicalCaseService.mapMedCaseToTapRequestDto(listMedCases)
    }
}