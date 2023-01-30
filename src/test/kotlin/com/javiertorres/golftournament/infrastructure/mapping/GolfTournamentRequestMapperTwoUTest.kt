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

internal class GolfTournamentRequestMapperTwoUTest {
    private val mapper = jacksonObjectMapper()
    private val testObj = GolfTournamentRequestMapperTwo

    @Test
    fun `Given an correct json request When mapping returns a GolfTournament`() {
        var request = CreateRequestFeature.getSourceTwo()
        var golfTournament = testObj.map(SourceData.TWO, mapper.readTree(request))

        assertThat(golfTournament.country.country).isEqualTo("US")
        assertThat(golfTournament.courseName).isEqualTo("Happy Days Golf Club")
        assertThat(golfTournament.startDate.toString()).isEqualTo("2021-12-01")
        assertThat(golfTournament.endDate.toString()).isEqualTo("2021-12-02")
        assertThat(golfTournament.externalId).isEqualTo("87fc6650-e114-4179-9aef-6a9a13030f80")
        assertThat(golfTournament.round).isEqualTo(2)
        assertThat(golfTournament.sourceTournament).isEqualTo(SourceData.TWO)
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
                    "tournamentUUID":"87fc6650-e114-4179-9aef-6a9a13030f80",
                    "golfCourse":"Happy Days Golf Club",
                    "competitionName":"South West Invitational",
                    "hostCountry":"INVALID",
                    "epochStart":"1638349200",
                    "epochFinish":"1638468000",
                    "rounds":"2",
                    "playerCount":"35"
                }
                """.trimIndent()
            ),
            Arguments.of(
                """
                {
                    "tournamentUUID":"87fc6650-e114-4179-9aef-6a9a13030f80",
                    "golfCourse":"Happy Days Golf Club",
                    "competitionName":"South West Invitational",
                    "hostCountry":"United States",
                    "epochStart":"INVALID",
                    "epochFinish":"1638468000",
                    "rounds":"2",
                    "playerCount":"35"
                }
                """.trimIndent()
            ),
            Arguments.of(
                """
                {
                    "tournamentUUID":"87fc6650-e114-4179-9aef-6a9a13030f80",
                    "golfCourse":"Happy Days Golf Club",
                    "competitionName":"South West Invitational",
                    "hostCountry":"United States",
                    "epochStart":"1638349200",
                    "epochFinish":"INVALID",
                    "rounds":"2",
                    "playerCount":"35"
                }
                """.trimIndent()
            ),

            // Empties
            Arguments.of(""),
            Arguments.of(
                """
                {
                    "tournamentUUID":"",
                    "golfCourse":"Happy Days Golf Club",
                    "competitionName":"South West Invitational",
                    "hostCountry":"United States",
                    "epochStart":"1638349200",
                    "epochFinish":"1638468000",
                    "rounds":"2",
                    "playerCount":"35"
                }
                """.trimIndent()
            ),
            Arguments.of(
                """
                {
                    "tournamentUUID":"87fc6650-e114-4179-9aef-6a9a13030f80",
                    "golfCourse":"",
                    "competitionName":"South West Invitational",
                    "hostCountry":"United States",
                    "epochStart":"1638349200",
                    "epochFinish":"1638468000",
                    "rounds":"2",
                    "playerCount":"35"
                }
                """.trimIndent()
            ),
            Arguments.of(
                """
                {
                    "tournamentUUID":"87fc6650-e114-4179-9aef-6a9a13030f80",
                    "golfCourse":"Happy Days Golf Club",
                    "competitionName":"South West Invitational",
                    "hostCountry":"",
                    "epochStart":"1638349200",
                    "epochFinish":"1638468000",
                    "rounds":"2",
                    "playerCount":"35"
                }
                """.trimIndent()
            ),
            Arguments.of(
                """
                {
                    "tournamentUUID":"87fc6650-e114-4179-9aef-6a9a13030f80",
                    "golfCourse":"Happy Days Golf Club",
                    "competitionName":"South West Invitational",
                    "hostCountry":"United States",
                    "epochStart":"",
                    "epochFinish":"1638468000",
                    "rounds":"2",
                    "playerCount":"35"
                }
                """.trimIndent()
            ),
            Arguments.of(
                """
                {
                    "tournamentUUID":"87fc6650-e114-4179-9aef-6a9a13030f80",
                    "golfCourse":"Happy Days Golf Club",
                    "competitionName":"South West Invitational",
                    "hostCountry":"United States",
                    "epochStart":"1638349200",
                    "epochFinish":"",
                    "rounds":"2",
                    "playerCount":"35"
                }
                """.trimIndent()
            ),
            Arguments.of(
                """
                {
                    "tournamentUUID":"87fc6650-e114-4179-9aef-6a9a13030f80",
                    "golfCourse":"Happy Days Golf Club",
                    "competitionName":"South West Invitational",
                    "hostCountry":"United States",
                    "epochStart":"1638349200",
                    "epochFinish":"1638468000",
                    "rounds":"",
                    "playerCount":"35"
                }
                """.trimIndent()
            )
        )
    }
}