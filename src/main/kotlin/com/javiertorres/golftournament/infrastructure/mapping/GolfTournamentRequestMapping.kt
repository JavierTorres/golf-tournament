package com.javiertorres.golftournament.infrastructure.mapping

import com.fasterxml.jackson.databind.JsonNode
import com.javiertorres.golftournament.domain.GolfTournament
import com.javiertorres.golftournament.domain.SourceData

interface GolfTournamentRequestMapping {
    fun map(sourceData: SourceData, requestBody: JsonNode): GolfTournament
}