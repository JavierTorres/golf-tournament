package com.javiertorres.golftournament.infrastructure.mapping

import com.fasterxml.jackson.databind.JsonNode
import com.javiertorres.golftournament.domain.GolfTournament
import com.javiertorres.golftournament.domain.SourceData
import org.springframework.stereotype.Component

@Component
class GolfTournamentRequestMapper {
    private val mappers: Map<SourceData, GolfTournamentRequestMapping> = mapOf(
        SourceData.ONE to GolfTournamentRequestMapperOne,
        SourceData.TWO to GolfTournamentRequestMapperTwo
    )

    fun map(sourceData: SourceData, requestBody: JsonNode): GolfTournament =
        mappers.getValue(sourceData).map(sourceData, requestBody)
}