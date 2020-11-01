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
import ru.rashgild.promedator.data.enity.MedCase
import ru.rashgild.promedator.data.enity.Visit
import java.time.format.DateTimeFormatter

@Service
class MedicalCaseServiceImpl(
    @Autowired private val personService: PersonService,
    @Autowired private val medsysClient: MedsysClient,
    @Autowired private val dictionaryService: DictionaryService,
    @Autowired private val workPlaceService: WorkPlaceService
) : MedicalCaseService {

    @Value("\${lpu.id}")
    private val lpuId: Long = 0

    @Value("\${lpu.polyclinic.unit}")
    private val lpuPolyclinicUnit: Long = 82L

    override fun getMedicalCase(date: String): List<MedicalCaseDto> {
        return medsysClient.getMedicalCase(date)
            .validate()
            .mapToList(MedicalCaseDto::class.java)
    }

    override fun medCaseDtoToMedCase(medCaseDto: MedicalCaseDto): MedCase {
        val listVisit: MutableList<Visit> = ArrayList()
        medCaseDto.visits?.forEach { visit ->

            val person = personService.getPersonByData(medCaseDto.patient)
            val workStaff = visit.workStaff
            val doctor = personService.getPersonBySnil(workStaff.snils)
            val workPlaces = doctor?.personId?.let { personId -> workPlaceService.getWorkPlaceByPersonId(personId) }
            val validWorkPlaces = workPlaces
                ?.filter { it.endDate == null && lpuId == it.lpuId }
                ?.toList()

            var medStaffDictionary: MedStaffDictionaryDto? = null

            validWorkPlaces?.forEach { it ->
                val lpuSection = dictionaryService.getDictionaryLpuSections()
                    .stream()
                    .filter { lpuSectionDictionaryDto ->
                        lpuSectionDictionaryDto.lpuSectionId == it.lpuSectionId &&
                                lpuPolyclinicUnit == lpuSectionDictionaryDto.lpuUnitId
                    }
                    .findFirst()
                    .orElse(null)

                if (lpuSection != null) {
                    medStaffDictionary = dictionaryService.getDictionaryMedStaff(
                        lpuSection.lpuSectionId,
                        it.medWorkerId
                    ).firstOrNull()
                }
            }

            listVisit.add(
                Visit(
                    personId = person?.personId,
                    medStaffId = medStaffDictionary?.medStaffId,
                    lpuSectionId = medStaffDictionary?.lpuSectionId,
                    resultDiseaseTypeId = visit.diseaseTypeId,
                    serviceTypeId = visit.serviceTypeId,
                    visitTypeId = setVisitType(medCaseDto.visits.size, visit.diagCode),
                    visitDate = visit.visitDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    numCard = medCaseDto.numCard,
                    diagLid = medCaseDto.diagLid,
                    diagId = medCaseDto.diagLid,
                    treatmentClassId = 1,
                    payTypeId = 61,
                    medicalCareKindId = 87,
                    resultClassId = medCaseDto.resultClass,
                    isFinish = if (visit.firstVisit) true else null,
                    firstVisit = visit.firstVisit,
                    medsysId = visit.medsysId,
                    dairy = VisitDto.getDiary(visit),
                )
            )

        }
        return MedCase(listVisit)
    }

    override fun mapMedCaseToTapRequestDto(listMedCase: List<MedCase>): MutableList<EvnRequestDto?> {
        val evnRequestList: MutableList<EvnRequestDto?> = ArrayList()
        //TODO validate and filter
        listMedCase.forEach { medCase ->
            val visits: MutableList<EvnVisitsDto> = ArrayList()
            var tapRequestDto: EvnRequestDto? = null
            medCase.visit.stream().forEach {
                if (it.firstVisit) {
                    tapRequestDto = visitToEvn(it)
                } else {
                    visits.add(visitToEvnVisitDto(it))
                }
            }
            if (visits.isNotEmpty()) {
                tapRequestDto?.visits = visits
            }
            evnRequestList.add(tapRequestDto)
        }
        return evnRequestList
    }

    private fun visitToEvn(visit: Visit): EvnRequestDto {
        return EvnRequestDto(
            evn = EvnDto(
                personId = visit.personId,
                medStaffFactId = visit.medStaffId,
                lpuSectionId = visit.lpuSectionId,
                resultDeseaseTypeId = visit.resultDiseaseTypeId,
                serviceTypeId = visit.serviceTypeId,
                visitTypeId = visit.visitTypeId,
                evnSetDt = visit.visitDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                evnPlNumCard = visit.numCard.toString(),
                diagLid = visit.diagLid,
                diagId = visit.diagLid,
                treatmentClassId = visit.treatmentClassId,
                payTypeId = visit.payTypeId,
                medicalCareKindId = visit.medicalCareKindId,
                resultClassId = visit.resultClassId,
                isFinish = visit.isFinish.toString(),
            ),
            evnDiary = EvnXmlDiaryDto(
                evnXmlData = visit.dairy
            )
        )
    }

    private fun visitToEvnVisitDto(visit: Visit): EvnVisitsDto {
        return EvnVisitsDto(
            visit = EvnVisitDto(
                medStaffFactId = visit.medStaffId,
                lpuSectionId = visit.lpuSectionId,
                serviceTypeId = visit.serviceTypeId,
                diagId = visit.diagId,
                treatmentClassId = visit.treatmentClassId,
                payTypeId = visit.payTypeId,
                medicalCareKindId = visit.medicalCareKindId,
                evnSetDt = visit.visitDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                visitTypeId = visit.visitTypeId,
                medsysId = visit.medsysId,
            ),
            diary = EvnXmlDiaryDto(
                evnXmlData = visit.dairy
            )
        )
    }

    private fun setVisitType(count: Int, diag: String?): Long {
        return when {
            diag?.contains("Z")!! -> {
                106
            }
            count == 1 -> {
                103
            }
            else -> {
                102
            }
        }
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
        /*  val person = personService.getPersonByData(medCase.patient)
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
            }*/
    }
}