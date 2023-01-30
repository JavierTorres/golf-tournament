package com.javiertorres.golftournament.domain

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.util.Locale

@Document
data class GolfTournament(@Id
                          val id: ObjectId = ObjectId.get(),
                          val externalId: String,
                          val courseName: String,
                          val country: Locale,
                          val sourceTournament: SourceData,
                          val round: Short,
                          val startDate: LocalDate,
                          val endDate: LocalDate)
