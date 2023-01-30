package com.javiertorres.golftournament.feature

import com.javiertorres.golftournament.domain.GolfTournament
import com.javiertorres.golftournament.domain.SourceData
import org.bson.types.ObjectId
import java.time.LocalDate
import java.util.*

object GolfTournamentFeature {

    fun get(): GolfTournament = GolfTournament(
        id = ObjectId.get(),
        externalId = "externalId-1",
        courseName = "courseName-1",
        country = Locale.UK,
        sourceTournament = SourceData.ONE,
        round = 2,
        startDate = LocalDate.now(),
        endDate = LocalDate.now().plusDays(1)
    )
}