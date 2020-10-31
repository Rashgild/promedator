package ru.rashgild.promedator.dao

import khttp.responses.Response
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.rashgild.promedator.data.dto.medsys.PatientDto
import ru.rashgild.promedator.data.dto.promed.ResponseErrorDto
import ru.rashgild.promedator.exception.BadRequestWebException

@Service
open class PromedClient(
    @Value("\${promed.url}") private val url: String,
    @Value("\${promed.login}") private val login: String,
    @Value("\${promed.password}") private val password: String,
    @Value("\${promed.lpu.id}") private val lpuId: String
) : BaseWebClient() {

    companion object {
        private const val PERSON_ENDPOINT = "api/Person"
        private const val DATE_TABLE_ENDPOINT = "api/TimeTableGraf/TimeTableGrafbyMO"
        private const val TIME_TABLE_ENDPOINT = "api/TimeTableGraf/TimeTableGrafById"
        private const val LOGIN_ENDPOINT = "api/user/login"
        private const val LOGOUT_ENDPOINT = "api/user/logout"
        private const val WORK_PLACE_ENDPOINT = "api/WorkPlace"
        private const val DICTIONARY_LPU_SECTION = "api/Lpu/LpuSectionListByMO"
        private const val DICTIONARY_MED_STAFF = "api/MedStaffFactByMedPersonal"

        fun Response.validate(): Response {
            if (this.statusCode == 200) {
                val error = this.map(ResponseErrorDto::class.java)
                if (!error.message.isNullOrEmpty()) {
                    throw BadRequestWebException("error: \"${error.message}\"")
                } else {
                    return this
                }
            } else {
                throw BadRequestWebException("error: \"${this.statusCode}\"")
            }
        }
    }

    fun auth(): Response {
        return khttp.get(
            url = url + LOGIN_ENDPOINT,
            params = mapOf(
                "login" to login,
                "password" to password
            )
        )
    }

    fun logout(): Response {
        return khttp.get(
            url = url + LOGOUT_ENDPOINT
        )
    }

    fun getDate(date: String): Response {
        return khttp.get(
            url = url + DATE_TABLE_ENDPOINT,
            params = mapOf(
                "TimeTableGraf_beg" to date,
                "TimeTableGraf_end" to date,
                "Lpu_id" to lpuId
            ),
            headers = authService.getHeader()
        )
    }

    fun getTimeByDateId(dateId: Long): Response {
        return khttp.get(
            url = url + TIME_TABLE_ENDPOINT,
            params = mapOf(
                "TimeTableGraf_id" to dateId.toString()
            ),
            headers = authService.getHeader()
        )
    }

    fun getPerson(personId: PatientDto): Response {
        //PersonRequest
        return khttp.get(
            url = url + PERSON_ENDPOINT,
        )
    }

    fun getPersonById(personId: Long?): Response {
        return khttp.get(
            url = url + PERSON_ENDPOINT,
            params = mapOf(
                "Person_id" to personId.toString()
            ),
            headers = authService.getHeader()
        )
    }

    fun getPersonByData(patient: PatientDto): Response {
        return khttp.get(
            url = url + PERSON_ENDPOINT,
            params = mapOf(
                "PersonSurName_SurName" to patient.lastName,
                "PersonFirName_FirName" to patient.firstName,
                "PersonBirthDay_BirthDay" to patient.birthday.toString(),
                "PersonSnils_Snils" to patient.snils
            ),
            headers = authService.getHeader()
        )
    }

    fun getPersonBySnils(snils: String): Response {
        return khttp.get(
            url = url + PERSON_ENDPOINT,
            params = mapOf(
                "PersonSnils_Snils" to snils
            ),
            headers = authService.getHeader()
        )
    }

    fun getWorkPlaceByPerson(snils: String): Response {
        return khttp.get(
            url = url + WORK_PLACE_ENDPOINT,
            params = mapOf(
                "PersonSnils_Snils" to snils
            ),
            headers = authService.getHeader()
        )
    }

    /*    public ClientResponse getWorkPlaceByPersonId(int personId) {
        Map.Entry<String, String> header = service.getHeader();
        return client
                .resource(promedEndpoint)
                .path(promedWorkPlace)
                .queryParam("person_id", String.valueOf(personId))
                .header(header.getKey(), header.getValue())
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
    }*/

    fun sendMedicalCase() {
        /*        return baseRequest(promedEvn)
                .post(ClientResponse.class, evn);*/
    }

    fun sendMedicalVisit() {
        /*        return baseRequest(promedVisit)
                .post(ClientResponse.class, evnVisit);*/
    }

    fun sendDiary() {
        /*        return baseRequest(promedDiary)
                .post(ClientResponse.class, evnXmlDiary);*/
    }

    fun getLpuDictionarySection(): Response {
        return khttp.get(
            url = url + DICTIONARY_LPU_SECTION,
            params = mapOf(
                "Lpu_id" to lpuId
            ),
            headers = authService.getHeader()
        )
    }

    fun getDictionaryMedstaff(lpuSectionId: Long, medPersonalId: Long): Response {
        return khttp.get(
            url = url + DICTIONARY_MED_STAFF,
            params = mapOf(
                "medPersonal_id" to lpuSectionId.toString(),
                "LpuSection_id" to medPersonalId.toString()
            ),
            headers = authService.getHeader()
        )
    }
}
