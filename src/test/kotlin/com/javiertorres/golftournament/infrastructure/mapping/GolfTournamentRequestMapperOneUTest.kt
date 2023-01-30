package com.javiertorres.golftournament.infrastructure.mapping

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.javiertorres.golftournament.domain.GolfTournamentRequestException
import com.javiertorres.golftournament.domain.SourceData
import com.javiertorres.golftournament.feature.CreateRequestFeature
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class GolfTournamentRequestMapperOneUTest {

    private val mapper = jacksonObjectMapper()
    private val testObj = GolfTournamentRequestMapperOne

    @Test
    fun `Given an correct json request When mapping returns a GolfTournament`() {
        val request = CreateRequestFeature.getSourceOne()
        var golfTournament = testObj.map(SourceData.ONE, mapper.readTree(request))

        assertThat(golfTournament.country.country).isEqualTo("GB")
        assertThat(golfTournament.courseName).isEqualTo("Sunnydale Golf Course")
        assertThat(golfTournament.startDate.toString()).isEqualTo("2021-07-09")
        assertThat(golfTournament.endDate.toString()).isEqualTo("2021-07-13")
        assertThat(golfTournament.externalId).isEqualTo("174638")
        assertThat(golfTournament.round).isEqualTo(4)
        assertThat(golfTournament.sourceTournament).isEqualTo(SourceData.ONE)
    }

    @ParameterizedTest
    @MethodSource("invalidRequests")
    fun `Given a json containing an invalid request When mapping throws GolfTournamentRequestException`(invalidRequest: String) {
        assertThrows(GolfTournamentRequestException::class.java) {
            testObj.map(SourceData.ONE, mapper.readTree(invalidRequest))
        }
    }

    companion object {
        @JvmStatic
        fun invalidRequests() = listOf(

            // Invalids
            Arguments.of(
                """
                {
                    "tournamentId": "174638",
                    "tournamentName": "Women's Open Championship",
                    "forecast": "fair",
                    "courseName": "Sunnydale Golf Course",
                    "countryCode": "INVALID",
                    "startDate": "09/07/21",
                    "endDate": "13/07/21",
                    "roundCount": "4"
                }
                """.trimIndent()),
            Arguments.of(
                """
                {
                    "tournamentId": "174638",
                    "tournamentName": "Women's Open Championship",
                    "forecast": "fair",
                    "courseName": "Sunnydale Golf Course",
                    "countryCode": "GB",
                    "startDate": "INVALID",
                    "endDate": "13/07/21",
                    "roundCount": "4"
                }
                """.trimIndent()),
            Arguments.of(
                """
                {
                    "tournamentId": "174638",
                    "tournamentName": "Women's Open Championship",
                    "forecast": "fair",
                    "courseName": "Sunnydale Golf Course",
                    "countryCode": "GB",
                    "startDate": "09/07/21",
                    "endDate": "INVALID",
                    "roundCount": "4"
                }
                """.trimIndent()),

            // Empties
            Arguments.of(""),
            Arguments.of(
                """
                {
                    "tournamentId": "",
                    "tournamentName": "Women's Open Championship",
                    "forecast": "fair",
                    "courseName": "Sunnydale Golf Course",
                    "countryCode": "GB",
                    "startDate": "09/07/21",
                    "endDate": "13/07/21",
                    "roundCount": "4"
                }
                """.trimIndent()),
            Arguments.of(
                """
                {
                    "tournamentId": "174638",
                    "tournamentName": "Women's Open Championship",
                    "forecast": "fair",
                    "courseName": "",
                    "countryCode": "GB",
                    "startDate": "09/07/21",
                    "endDate": "13/07/21",
                    "roundCount": "4"
                }
                """.trimIndent()),
            Arguments.of(
                """
                {
                    "tournamentId": "174638",
                    "tournamentName": "Women's Open Championship",
                    "forecast": "fair",
                    "courseName": "Sunnydale Golf Course",
                    "countryCode": "",
                    "startDate": "09/07/21",
                    "endDate": "13/07/21",
                    "roundCount": "4"
                }
                """.trimIndent()),
            Arguments.of(
                """
                {
                    "tournamentId": "174638",
                    "tournamentName": "Women's Open Championship",
                    "forecast": "fair",
                    "courseName": "Sunnydale Golf Course",
                    "countryCode": "GB",
                    "startDate": "",
                    "endDate": "13/07/21",
                    "roundCount": "4"
                }
                """.trimIndent()),
            Arguments.of(
                """
                {
                    "tournamentId": "174638",
                    "tournamentName": "Women's Open Championship",
                    "forecast": "fair",
                    "courseName": "Sunnydale Golf Course",
                    "countryCode": "GB",
                    "startDate": "09/07/21",
                    "endDate": "",
                    "roundCount": "4"
                }
                """.trimIndent()),
            Arguments.of(
                """
                {
                    "tournamentId": "174638",
                    "tournamentName": "Women's Open Championship",
                    "forecast": "fair",
                    "courseName": "Sunnydale Golf Course",
                    "countryCode": "GB",
                    "startDate": "09/07/21",
                    "endDate": "13/07/21",
                    "roundCount": ""
                }
                """.trimIndent()
            )
        )
    }
}