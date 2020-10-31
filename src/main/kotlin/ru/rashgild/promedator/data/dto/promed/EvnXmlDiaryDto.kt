package ru.rashgild.promedator.data.dto.promed

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Дневник.
 */
data class EvnXmlDiaryDto(
    @JsonProperty("Evn_id")
    var evnId: String? = null,

    @JsonProperty("XmlType_id")
    private val xmlTypeId: String = "3",

    @JsonProperty("EvnXml_Data")
    private val evnXmlData: String? = null,

    @JsonProperty("XmlTemplateHtml_HtmlTemplate")
    private val xmlHtmlTemplate: String = ("<div class='printonly' style='display: block;'><div class='template-block' "
            + "id='block_autoname47'><p class='template-block-caption' "
            + "id='caption_autoname47'><span style='font-weight: bold; font-size:14px;'/></p>"
            + "<div class='template-block-data' id='data_autoname47'>{autoname47}</div></div><br/></div>"),

    @JsonProperty("XmlTemplate_id")
    private val xmlTemplateId: Int = 221314
)