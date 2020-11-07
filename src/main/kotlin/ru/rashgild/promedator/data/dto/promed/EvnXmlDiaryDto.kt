package ru.rashgild.promedator.data.dto.promed

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Дневник.
 */
data class EvnXmlDiaryDto(

    private val XML_TEMPLATE: String = ("<div class='printonly' style='display: block;'><div class='template-block' "
            + "id='block_autoname47'><p class='template-block-caption' "
            + "id='caption_autoname47'><span style='font-weight: bold; font-size:14px;'/></p>"
            + "<div class='template-block-data' id='data_autoname47'>{autoname47}</div></div><br/></div>"),

    @JsonProperty("Evn_id") var evnId: String? = null,
    @JsonProperty("XmlType_id") val xmlTypeId: String = "3",
    @JsonProperty("EvnXml_Data") val evnXmlData: String,
    @JsonProperty("XmlTemplateHtml_HtmlTemplate") val xmlHtmlTemplate: String = XML_TEMPLATE,
    @JsonProperty("XmlTemplate_id") val xmlTemplateId: Int = 221314
)