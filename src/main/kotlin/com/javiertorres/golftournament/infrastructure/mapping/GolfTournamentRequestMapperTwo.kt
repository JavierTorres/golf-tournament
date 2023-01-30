package com.javiertorres.golftournament.infrastructure.mapping

import com.fasterxml.jackson.databind.JsonNode
import com.javiertorres.golftournament.domain.GolfTournament
import com.javiertorres.golftournament.domain.GolfTournamentRequestException
import com.javiertorres.golftournament.domain.SourceData
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Locale

object GolfTournamentRequestMapperTwo: GolfTournamentRequestMapping {
    override fun map(sourceData: SourceData, requestBody: JsonNode): GolfTournament {
        val tournamentUUID = getNotEmpty(requestBody, "tournamentUUID")
        val golfCourse = getNotEmpty(requestBody, "golfCourse")
        var rounds = getNotEmpty(requestBody, "rounds")
        val hostCountry = getNotEmpty(requestBody, "hostCountry")
        var epochStart = getNotEmpty(requestBody, "epochStart")
        var epochFinish = getNotEmpty(requestBody, "epochFinish")

        val country = Locale.getAvailableLocales()
            .find { locale -> locale.displayCountry.contains(hostCountry) }
            .takeIf { it != null } ?: throw GolfTournamentRequestException("Invalid name")


        val startDate = toLocalDate(epochStart)
        val endDate = toLocalDate(epochFinish)

        return GolfTournament(
            externalId = tournamentUUID,
            courseName = golfCourse,
            sourceTournament = sourceData,
            round = rounds.toShort(),
            startDate = startDate,
            endDate = endDate,
            country = country
        )
    }

    private fun getNotEmpty(requestBody: JsonNode, param: String): String {
        val paramValue = requestBody.get(param) ?: throw GolfTournamentRequestException("$param can not be null")
        return paramValue.textValue().takeIf { it.isNotBlank() } ?: throw GolfTournamentRequestException("$param can not be blank")
    }

    private fun toLocalDate(epochStr: String): LocalDate {
        try {
            val epoch = epochStr.toLong()
            return Instant.ofEpochSecond(epoch).atZone(ZoneId.systemDefault()).toLocalDate()
        } catch (e: RuntimeException) {
            throw GolfTournamentRequestException("Invalid epoch [$epochStr]")
        }
    }
}