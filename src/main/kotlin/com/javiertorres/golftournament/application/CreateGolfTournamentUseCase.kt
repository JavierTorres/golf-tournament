package com.javiertorres.golftournament.application

import com.javiertorres.golftournament.domain.GolfTournament
import com.javiertorres.golftournament.infrastructure.mongo.GolfTournamentRepository
import org.springframework.stereotype.Component

@Component
class CreateGolfTournamentUseCase(private val golfTournamentRepository: GolfTournamentRepository) {
    fun exec(golfTournament: GolfTournament): GolfTournament = golfTournamentRepository.insert(golfTournament)
}