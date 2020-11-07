package ru.rashgild.promedator.mapping

import org.springframework.stereotype.Component
import ru.rashgild.promedator.data.dto.medsys.MedicalCaseDto
import ru.rashgild.promedator.data.dto.medsys.VisitDto
import ru.rashgild.promedator.data.dto.promed.*
import ru.rashgild.promedator.data.enity.Visit
import java.time.format.DateTimeFormatter

@Component
class VisitMapper {

    fun mapToVisit(medCaseDto: MedicalCaseDto, visit: VisitDto): Visit {
      return Visit(
            personId = medCaseDto.personDto?.personId,
            medStaffId = visit.medStaffDictionary?.medStaffId,
            lpuSectionId = visit.medStaffDictionary?.lpuSectionId,
            resultDiseaseTypeId = visit.diseaseTypeId,
            serviceTypeId = visit.serviceTypeId,
            visitTypeId = setVisitType(medCaseDto.visits?.size, visit.diagCode),
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
    }

    fun mapToEvnDto(visit: Visit): EvnRequestDto {
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

    fun mapToVisitDto(visit: Visit): EvnVisitsDto{
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

    private fun setVisitType(count: Int?, diag: String?): Long {
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
}