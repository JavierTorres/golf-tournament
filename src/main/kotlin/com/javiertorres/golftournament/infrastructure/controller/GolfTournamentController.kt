package com.javiertorres.golftournament.infrastructure.controller

import com.fasterxml.jackson.databind.JsonNode
import com.javiertorres.golftournament.application.CreateGolfTournamentUseCase
import com.javiertorres.golftournament.domain.GolfTournament
import com.javiertorres.golftournament.domain.SourceData
import com.javiertorres.golftournament.infrastructure.mapping.GolfTournamentRequestMapper
import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class GolfTournamentController(
    private val createGolfTournamentUseCase: CreateGolfTournamentUseCase,
    private val golfTournamentRequestMapper: GolfTournamentRequestMapper) {

    @PostMapping("/v1/golf-tournament")
    fun post(@RequestHeader("sourceData") sourceData: SourceData, @RequestBody requestBody: JsonNode):
            ResponseEntity<GolfTournament> {

        LOGGER.debug { "Processing a POST GolfTournament request for the sourceData [$sourceData] and requestBody [$requestBody]" }
        var golfTournament = golfTournamentRequestMapper.map(sourceData, requestBody)
        golfTournament = createGolfTournamentUseCase.exec(golfTournament)
        LOGGER.debug { "GolfTournament being created [$golfTournament]" }

        return ResponseEntity.ok(golfTournament)
    }

    companion object {
        private val LOGGER = KotlinLogging.logger {}
    }
}

