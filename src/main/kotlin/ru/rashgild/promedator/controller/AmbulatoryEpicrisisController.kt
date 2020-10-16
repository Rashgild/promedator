package ru.rashgild.promedator.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/ambulatory")
class AmbulatoryEpicrisisController {

    @GetMapping(value = ["/epicrisis-export"], produces = ["application/json"])
    fun epicExport(
        @RequestParam(value = "dateFrom") date: String?,
        @RequestParam(value = "dateTo", required = false) dateTo: String?
    ): ResponseEntity<Any>? {
        return ResponseEntity(HttpStatus.OK)
    }
}