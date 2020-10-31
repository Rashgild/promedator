package ru.rashgild.promedator.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.rashgild.promedator.dao.BaseWebClient.Companion.mapToList
import ru.rashgild.promedator.dao.BaseWebClient.Companion.validate
import ru.rashgild.promedator.dao.MedsysClient
import ru.rashgild.promedator.data.dto.medsys.MedicalCaseDto
import ru.rashgild.promedator.data.dto.promed.EvnRequestDto
import ru.rashgild.promedator.data.dto.promed.ResponseModelDto
import ru.rashgild.promedator.data.dto.promed.dictionary.MedStaffDictionaryDto

@Service
class MedicalCaseServiceImpl(
    @Autowired private val personService: PersonService,
    @Autowired private val medsysClient: MedsysClient,
    @Autowired private val dictionaryService: DictionaryService,
    @Autowired private val workPlaceService: WorkPlaceService
) : MedicalCaseService {

    @Value("\${lpu.id}")
    private val lpuId: Long = 0

    private val LPU_POLYCLICNIC_UNIT = 82L //TODO to settings

    override fun getMedicalCase(date: String): List<MedicalCaseDto> {
        return medsysClient.getMedicalCase(date)
            .validate()
            .mapToList(MedicalCaseDto::class.java)
    }


    private fun medsysToEvnDto(medCase: MedicalCaseDto): EvnRequestDto? {
        medCase.visits?.forEach { visit ->

            if (medCase.resultClass == null) {
                ResponseModelDto(
                    objId = visit.medsysId,
                    errorMessage = "resultClass is empty"
                )
            }
            if (visit.diagCode.isNullOrEmpty()) {
                ResponseModelDto(
                    objId = visit.medsysId,
                    errorMessage = "diagnosis  code is empty"
                )
            }
            if (visit.diary.isNullOrEmpty()) {
                ResponseModelDto(
                    objId = visit.medsysId,
                    errorMessage = "diary is empty"
                )
            }
            val person = personService.getPersonByData(medCase.patient)
            if (person == null) {
                ResponseModelDto(
                    objId = visit.medsysId,
                    errorMessage = "person not found"
                )
            }

            val workStaff = visit.workStaff
            val doctor = personService.getPersonBySnil(workStaff.snils)
            if (doctor == null) {
                ResponseModelDto(
                    objId = visit.medsysId,
                    errorMessage = "doctor not found"
                )
            }

            val workPlaces = doctor?.personId?.let { personId -> workPlaceService.getWorkPlaceByPersonId(personId) }
            if (workPlaces.isNullOrEmpty()) {
                ResponseModelDto(
                    objId = visit.medsysId,
                    errorMessage = "doctor workPlace not found"
                )
            }

            val validWorkPlaces = workPlaces
                ?.filter { it.endDate == null && lpuId == it.lpuId }
                ?.toList()

            var medstaffDictionary: List<MedStaffDictionaryDto>? = null
            validWorkPlaces?.forEach{ it ->
                val lpuSection = dictionaryService.getDictionaryLpuSections()
                    .stream()
                    .filter { lpuSectionDictionary ->
                        lpuSectionDictionary.lpuSectionId == it.lpuSectionId
                                && LPU_POLYCLICNIC_UNIT == lpuSectionDictionary.lpuUnitId
                    }
                    .findFirst()
                    .orElse(null)


                if (lpuSection != null) {
                    medstaffDictionary = dictionaryService.getDictionaryMedStaff(
                        lpuSection.lpuSectionId,
                        it.medWorkerId
                    )
                }
                if (medstaffDictionary != null) {
                    //break
                }
            }

            if (medstaffDictionary.isNullOrEmpty()) {
                ResponseModelDto(
                    objId = visit.medsysId,
                    errorMessage = "doctor has no workfunction"
                )
            }

        }
        return null
    }

}