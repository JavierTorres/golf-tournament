package com.javiertorres.golftournament.infrastructure.mongo

import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.utility.DockerImageName

class MongoContainer : MongoDBContainer(MONGO_IMAGE_NAME) {

    override fun getReplicaSetUrl(): String {
        return super.getReplicaSetUrl(DATABASE_NAME)
    }

    override fun getReplicaSetUrl(databaseName: String?): String {
        return super.getReplicaSetUrl(databaseName).toString() + CONNECTION_PARAMS
    }

    companion object {
        private val MONGO_IMAGE_NAME = DockerImageName.parse("mongo").withTag("5.0.4")
        private const val DATABASE_NAME = "golf-tournament"
        private const val CONNECTION_PARAMS = "?serverSelectionTimeoutMS=1000&connectTimeoutMS=1000"
    }
}