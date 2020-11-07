package ru.rashgild.promedator.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.rashgild.promedator.dao.BaseWebClient.Companion.mapToList
import ru.rashgild.promedator.dao.BaseWebClient.Companion.validate
import ru.rashgild.promedator.dao.MedsysClient
import ru.rashgild.promedator.dao.PromedClient
import ru.rashgild.promedator.data.dto.medsys.MedicalCaseDto
import ru.rashgild.promedator.data.dto.medsys.VisitDto
import ru.rashgild.promedator.data.dto.promed.*
import ru.rashgild.promedator.data.dto.promed.dictionary.MedStaffDictionaryDto
import ru.rashgild.promedator.data.enity.MedCase
import ru.rashgild.promedator.data.enity.Visit
import ru.rashgild.promedator.mapping.VisitMapper
import java.time.format.DateTimeFormatter

@Service
class MedicalCaseServiceImpl(
    @Autowired private val personService: PersonService,
    @Autowired private val medsysClient: MedsysClient,
    @Autowired private val promedClient: PromedClient,
    @Autowired private val dictionaryService: DictionaryService,
    @Autowired private val workPlaceService: WorkPlaceService,
    @Autowired private val visitMapper: VisitMapper
) : MedicalCaseService {

    @Value("\${promed.lpu.id}")
    private val lpuId: Long = 0

    @Value("\${promed.lpu.polyclinic}")
    private val lpuPolyclinicUnit: Long = 82L

    override fun getMedicalCase(date: String): List<MedicalCaseDto> {
        return medsysClient.getMedicalCase(date)
            .validate()
            .mapToList(MedicalCaseDto::class.java)
    }

    override fun sendEvn(evn: EvnDto) {
        promedClient.sendEvn(evn)
    }

    fun sendDiary(dairy: EvnXmlDiaryDto) {
        promedClient.sendDiary(dairy)
    }

    fun sendVisit(visit: EvnVisitDto) {
        promedClient.sendEvnVisit(visit)
    }

    override fun medCaseDtoToMedCase(medCaseDto: MedicalCaseDto): MedCase {
        val listVisit: MutableList<Visit> = ArrayList()
        initFields(medCaseDto)
        medCaseDto.visits?.forEach { visit ->
            listVisit.add(visitMapper.mapToVisit(medCaseDto, visit))
        }
        return MedCase(listVisit)
    }

    override fun mapMedCaseToTapRequestDto(listMedCase: List<MedCase>): MutableList<EvnRequestDto?> {
        val evnRequestList: MutableList<EvnRequestDto?> = ArrayList()
        //TODO validate and filter

        listMedCase
            .forEach { medCase ->
                val visits: MutableList<EvnVisitsDto> = ArrayList()
                var tapRequestDto: EvnRequestDto? = null
                medCase.visit.stream().forEach {
                    if (it.firstVisit) {
                        tapRequestDto = visitMapper.mapToEvnDto(it)
                    } else {
                        visits.add(visitMapper.mapToVisitDto(it))
                    }
                }
                if (visits.isNotEmpty()) {
                    tapRequestDto?.visits = visits
                }
                evnRequestList.add(tapRequestDto)
            }
        return evnRequestList
    }

    private fun export(evnList: MutableList<EvnRequestDto>) {
        evnList
            .parallelStream()
            .forEach {
                promedClient.sendEvn(it.evn) //TODO return baseId from promed
                promedClient.sendDiary(it.evnDiary)
                it.visits?.forEach { visit ->
                    promedClient.sendEvnVisit(visit.visit) //TOOD return visitId from promed;
                    promedClient.sendDiary(visit.diary)
                }
            }
    }

    private fun getMedStaffDictionary(visit: VisitDto): MedStaffDictionaryDto? {
        val doctor = personService.getPersonBySnils(visit.workStaff.snils)
        val workPlacesList = doctor?.personId?.let { personId -> workPlaceService.getWorkPlaceByPersonId(personId) }
        val validWorkPlace = workPlacesList?.firstOrNull { it.endDate == null && lpuId == it.lpuId }

        return if (validWorkPlace != null) {
            val lpuSection = dictionaryService.getDictionaryLpuSections()
                .firstOrNull { lpuSectionDictionaryDto ->
                    lpuSectionDictionaryDto.lpuSectionId == validWorkPlace.lpuSectionId &&
                            lpuPolyclinicUnit == lpuSectionDictionaryDto.lpuUnitId
                }
            dictionaryService.getDictionaryMedStaff(
                lpuSection!!.lpuSectionId,
                validWorkPlace.medWorkerId
            ).firstOrNull()

        } else {
            null
        }
    }

    private fun initFields(medCaseDto: MedicalCaseDto) {
        medCaseDto.personDto = personService.getPersonByData(medCaseDto.patient)!!
        medCaseDto.visits?.forEach { visit -> visit.medStaffDictionary = getMedStaffDictionary(visit)!! }
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
