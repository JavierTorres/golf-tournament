package com.javiertorres.golftournament.infrastructure.mongo.db.changelog

import com.github.cloudyrock.mongock.ChangeLog
import com.github.cloudyrock.mongock.ChangeSet
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate
import com.javiertorres.golftournament.domain.GolfTournament
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.index.Index

@ChangeLog
class DatabaseChangelog001 {

    @ChangeSet(order = "001", id = "createGolfTournamentCollection", author = "Javier.Torres")
    fun createGolfTournamentCollection(mongockTemplate: MongockTemplate) =
        mongockTemplate.createCollection(GolfTournament::class.java)


    @ChangeSet(order = "002", id = "setIndexGolfTournamentExternalId", author = "Javier.Torres")
    fun setGolfTournamentExternalIdIndex(mongockTemplate: MongockTemplate) =
        mongockTemplate.indexOps(GolfTournament::class.java)
            .ensureIndex(Index()
                .named("externalId")
                .on("externalId", Sort.Direction.ASC)
            )
}