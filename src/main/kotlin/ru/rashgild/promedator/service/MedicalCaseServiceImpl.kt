package ru.rashgild.promedator.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.rashgild.promedator.dao.BaseWebClient.Companion.mapToList
import ru.rashgild.promedator.dao.BaseWebClient.Companion.validate
import ru.rashgild.promedator.dao.MedsysClient
import ru.rashgild.promedator.data.dto.medsys.MedicalCaseDto
import ru.rashgild.promedator.data.dto.medsys.VisitDto
import ru.rashgild.promedator.data.dto.promed.*
import ru.rashgild.promedator.data.dto.promed.dictionary.MedStaffDictionaryDto
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

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


    private fun validate(medCase: MedicalCaseDto, visit: VisitDto) {
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

        if (visit.diary.isEmpty()) {
            ResponseModelDto(
                objId = visit.medsysId,
                errorMessage = "diary is empty"
            )
        }
    }

    private fun medsysToEvnDto(medCase: MedicalCaseDto): EvnRequestDto? {
        medCase.visits?.forEach { visit ->

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

            var medStaffDictionary: List<MedStaffDictionaryDto>? = null
            validWorkPlaces?.forEach { it ->
                val lpuSection = dictionaryService.getDictionaryLpuSections()
                    .stream()
                    .filter { lpuSectionDictionary ->
                        lpuSectionDictionary.lpuSectionId == it.lpuSectionId
                                && LPU_POLYCLICNIC_UNIT == lpuSectionDictionary.lpuUnitId
                    }
                    .findFirst()
                    .orElse(null)


                if (lpuSection != null) {
                    medStaffDictionary = dictionaryService.getDictionaryMedStaff(
                        lpuSection.lpuSectionId,
                        it.medWorkerId
                    )
                }
                if (medStaffDictionary != null) {
                    //break
                }
            }

            if (medStaffDictionary.isNullOrEmpty()) {
                ResponseModelDto(
                    objId = visit.medsysId,
                    errorMessage = "doctor has no workfunction"
                )
            }
        }
        return null
    }

    private fun converting(
        person: PersonDto,
        medStaff: MedStaffDictionaryDto,
        visit: VisitDto,
        medCase: MedicalCaseDto
    ) {

        val visits: MutableList<EvnVisitsDto> = ArrayList()
        var tapRequestDto: EvnRequestDto? = null
        if (visit.firstVisit) {
            tapRequestDto = EvnRequestDto(
                evn = EvnDto(
                    personId = person.personId,
                    medStaffFactId = medStaff.medStaffId,
                    lpuSectionId = medStaff.lpuSectionId,
                    resultDeseaseTypeId = visit.diseaseTypeId,
                    serviceTypeId = visit.serviceTypeId,
                    visitTypeId = visit.visitTypeId,
                    evnSetDt = visit.visitDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    evnPlNumCard = medCase.numCard.toString(),
                    diagLid = medCase.diagLid,
                    diagId = medCase.diagLid,
                    treatmentClassId = 1, //TODO to constants
                    payTypeId = 61,
                    medicalCareKindId = 87,
                    resultClassId = medCase.resultClass,
                    isFinish = "1",
                ),
                evnDiary = EvnXmlDiaryDto(
                    evnXmlData = VisitDto.getDiary(visit)
                )
            )
        } else {
            val list = EvnVisitsDto(
                visit = EvnVisitDto(
                    medStaffFactId = medStaff.medStaffId,
                    lpuSectionId = medStaff.lpuSectionId,
                    serviceTypeId = visit.serviceTypeId,
                    diagId = visit.diagId,
                    treatmentClassId = 1,
                    payTypeId = 61,
                    medicalCareKindId = 87,
                    evnSetDt = visit.visitDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    visitTypeId = visit.visitTypeId,//(countVisits)),
                    medsysId = visit.medsysId, // . setMedosId visit.getMedosId()
                ),
                diary = EvnXmlDiaryDto(
                    evnXmlData = VisitDto.getDiary(visit)
                )
            )
            visits.add(list)
        }

        if (visits.isNotEmpty()) {
            tapRequestDto?.visits = visits
        }

    }

}