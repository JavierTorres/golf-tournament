package com.javiertorres.golftournament.infrastructure.mongo

import com.javiertorres.golftournament.domain.GolfTournament
import org.springframework.data.mongodb.repository.MongoRepository

interface GolfTournamentRepository: MongoRepository<GolfTournament, String>