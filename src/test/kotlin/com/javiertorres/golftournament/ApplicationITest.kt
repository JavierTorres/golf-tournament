package com.javiertorres.golftournament

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.javiertorres.golftournament.domain.SourceData
import com.javiertorres.golftournament.feature.CreateRequestFeature
import com.javiertorres.golftournament.infrastructure.mongo.MongoContainer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.net.URI

@SpringBootTest(
    classes = [Application::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class AplicationITest {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @LocalServerPort
    var randomServerPort: Int = 0

    private val mapper = jacksonObjectMapper()

    @ParameterizedTest
    @MethodSource("validRequests")
    fun `Given a create a golfTournament request and MongoDB UP When processing the request Then it returns OK`
                (sourceData: SourceData, requestBody: String) {

        val baseUrl = "http://localhost:$randomServerPort/$END_POINT"
        val uri = URI(baseUrl)
        val headers = HttpHeaders()
        headers.set("Content-Type", "application/json")
        headers.set("sourceData", sourceData.name)

        var requestBodyJsonNode = mapper.readTree(requestBody)
        val request: HttpEntity<JsonNode> = HttpEntity(requestBodyJsonNode, headers)

        val result = restTemplate.postForEntity(
            uri, request,
            String::class.java
        )

        assertNotNull(result)
        assertEquals(HttpStatus.OK, result?.statusCode)
    }

    companion object {
        const val END_POINT = "v1/golf-tournament"

        @Container
        private val MONGO_CONTAINER = MongoContainer()

        @DynamicPropertySource
        @JvmStatic
        fun registerDynamicProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.data.mongodb.uri", MONGO_CONTAINER::getReplicaSetUrl)
        }

        @JvmStatic
        fun validRequests() = listOf(
            Arguments.of(SourceData.ONE, CreateRequestFeature.getSourceOne()),
            Arguments.of(SourceData.TWO, CreateRequestFeature.getSourceTwo())
        )
    }
}