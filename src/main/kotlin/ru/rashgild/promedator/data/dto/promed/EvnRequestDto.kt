package ru.rashgild.promedator.data.dto.promed

data class EvnRequestDto(
    var evn: EvnDto? = null,
    val evnDiary: EvnXmlDiaryDto,
    var visits: List<EvnVisitsDto>? = emptyList(),
    val hasError: Boolean = false,
    val responseModels: ResponseModelDto? = null
)