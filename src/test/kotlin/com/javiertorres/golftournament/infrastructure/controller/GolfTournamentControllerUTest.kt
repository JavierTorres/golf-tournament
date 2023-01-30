package com.javiertorres.golftournament.infrastructure.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.javiertorres.golftournament.application.CreateGolfTournamentUseCase
import com.javiertorres.golftournament.domain.SourceData
import com.javiertorres.golftournament.feature.CreateRequestFeature
import com.javiertorres.golftournament.feature.GolfTournamentFeature
import com.javiertorres.golftournament.infrastructure.mapping.GolfTournamentRequestMapper
import com.nhaarman.mockito_kotlin.given
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest
internal class GolfTournamentControllerUTest(@Autowired val mockMvc: MockMvc) {

    @MockBean
    lateinit var createGolfTournamentUseCase: CreateGolfTournamentUseCase
    @MockBean
    lateinit var golfTournamentRequestMapper: GolfTournamentRequestMapper
    @MockBean
    lateinit var mongoTemplate: MongoTemplate

    private val mapper = jacksonObjectMapper()
    private val golfTournament = GolfTournamentFeature.get()

    @ParameterizedTest
    @MethodSource("validRequests")
    fun `Given a create a golfTournament request When executing processing the request Then it returns the golfTournament`
                (sourceData: SourceData, requestBody: String) {
        given(golfTournamentRequestMapper.map(sourceData, mapper.readTree(requestBody))).willReturn(golfTournament)
        given(createGolfTournamentUseCase.exec(golfTournament)).willReturn(golfTournament)

        mockMvc.perform(post(END_POINT)
            .header("sourceData", sourceData)
            .content(requestBody).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.externalId").value(golfTournament.externalId))
            .andExpect(jsonPath("$.round").value(golfTournament.round.toString()))
            .andExpect(jsonPath("$.country").value(golfTournament.country.toString()))
            .andExpect(jsonPath("$.sourceTournament").value(golfTournament.sourceTournament.name))
            .andExpect(jsonPath("$.startDate").value(golfTournament.startDate.toString()))
            .andExpect(jsonPath("$.endDate").value(golfTournament.endDate.toString()));
    }

    @Test
    fun `Given a create golfTournament request missing the sourceData header When executing Then it returns bad request`() {
        var requestBody = CreateRequestFeature.getSourceOne()

        mockMvc.perform(post("/v1/golf-tournament")
            .content(requestBody).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `Given a create golfTournament request missing the body When executing Then it returns bad request`() {
        mockMvc.perform(post(END_POINT)
            .header("sourceData", SourceData.ONE))
            .andExpect(status().isBadRequest)
    }

    companion object {
        const val END_POINT = "/v1/golf-tournament"

        @JvmStatic
        fun validRequests() = listOf(
            Arguments.of(SourceData.ONE, CreateRequestFeature.getSourceOne()),
            Arguments.of(SourceData.TWO, CreateRequestFeature.getSourceTwo())
        )
    }
}