package ru.rashgild.promedator.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.rashgild.promedator.dao.BaseWebClient.Companion.mapToList
import ru.rashgild.promedator.dao.MedsysClient
import ru.rashgild.promedator.data.dto.BaseResponseDto

@Service
class AmbulatoryEpicrisisServiceImpl(
    @Autowired private val medsysClient: MedsysClient
) : AmbulatoryEpicrisisService {

    override fun sync(date: String) {
        medsysClient.getMedicalCase(date).mapToList(BaseResponseDto::class.java)
    }
}