package com.javiertorres.golftournament.application

import com.javiertorres.golftournament.feature.GolfTournamentFeature
import com.javiertorres.golftournament.infrastructure.mongo.GolfTournamentRepository
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class CreateGolfTournamentUseCaseUTest {

    private val golfTournamentRepository: GolfTournamentRepository = mock()
    private val testOjb = CreateGolfTournamentUseCase(golfTournamentRepository)

    @Test
    fun `Given a golfTournament When executing the use case Then it returns the golfTournament`() {
        var golfTournament = GolfTournamentFeature.get()
        given(golfTournamentRepository.insert(golfTournament)).willReturn(golfTournament)
        assertEquals(golfTournament, testOjb.exec(golfTournament))
    }
}