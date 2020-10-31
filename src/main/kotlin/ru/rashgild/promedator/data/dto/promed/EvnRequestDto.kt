package ru.rashgild.promedator.data.dto.promed

data class EvnRequestDto(
    private var evn: EvnDto? = null,
    private val evnDiary: EvnXmlDiaryDto,
    private val visits: List<EvnVisitsDto>,
    private val hasError: Boolean = false,
    private val responseModels: ResponseModelDto? = null
)