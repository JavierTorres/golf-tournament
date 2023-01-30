package com.javiertorres.golftournament.infrastructure.mapping

import com.fasterxml.jackson.databind.JsonNode
import com.javiertorres.golftournament.domain.GolfTournament
import com.javiertorres.golftournament.domain.GolfTournamentRequestException
import com.javiertorres.golftournament.domain.SourceData
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

object GolfTournamentRequestMapperOne: GolfTournamentRequestMapping {

    private var formatter = DateTimeFormatter.ofPattern("dd/MM/yy")

    override fun map(sourceData: SourceData, requestBody: JsonNode): GolfTournament {

        val tournamentId = getNotEmpty(requestBody, "tournamentId")
        val courseName = getNotEmpty(requestBody, "courseName")
        var roundCount = getNotEmpty(requestBody, "roundCount")
        val startDateRequest = getNotEmpty(requestBody,"startDate")
        val endDateRequest = getNotEmpty(requestBody, "endDate")
        val countryCode = getNotEmpty(requestBody, "countryCode")

        val country = Locale.getAvailableLocales()
            .find { locale -> locale.country.equals(countryCode) }
            .takeIf { it != null } ?: throw GolfTournamentRequestException("Invalid country code")

        val startDate = toLocalDate(startDateRequest)
        var endDate = toLocalDate(endDateRequest)

        return GolfTournament(
            externalId = tournamentId,
            courseName = courseName,
            sourceTournament = sourceData,
            round = roundCount.toShort(),
            startDate = startDate,
            endDate = endDate,
            country = country
        )
    }

    private fun getNotEmpty(requestBody: JsonNode, param: String): String {
        val paramValue = requestBody.get(param) ?: throw GolfTournamentRequestException("$param can not be null")
        return paramValue.textValue().takeIf { it.isNotBlank() } ?: throw GolfTournamentRequestException("$param can not be blank")
    }

    private fun toLocalDate(dateStr: String): LocalDate {
        try {
            return LocalDate.parse(dateStr, formatter)
        } catch (e: DateTimeParseException) {
            throw GolfTournamentRequestException("Invalid endDate [$dateStr]")
        }
    }
}