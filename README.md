# Golf Tournament

## Table of Contents

- [Features](#features)
- [Description](#description)  
- [Notes](#notes)
- [CMD](#cmd)

---

## Features

- Kotlin
- Gradle
- REST API
- Hexagonal Architecture
- SprintBoot
- MongoDB
- Mongock
- Docker  
- Unit Tests
- Integration Test
- TestContainers
- Mockito

---

## Description

This is a MVP microservice to store in MongoDB setting a golf tournament data from different JSON formats by a single endpoint.
MongoDB is being selected as a DB Storage setting two initial indexes that might help when searching in the future
There are two different Data sources, and the client will be responsible to provide from the HTTP Request Header which source format will be used.

### Adding additional sources

To add a new additional source, it will be as simple as creating a new mapper implementing the interface `GolfTournamentRequestMapping` 
and add it to the list of `mappers` on the `GolfTournamentRequestMapper`. Also, as a good practice, it would be recommended to add a UTest for that component

### Testing

It contains Unit tests `UTest` file to test individually each component
There is a `ApplicationITest` to run an integration test fully integrated with MongoDB using `TestContainers`

### DB storage

MongoDB is being selected a database storage as it provides flexibility in JSON format that might be required in the future.
Mongock is being selected as a database changes tool to create the collection `GolfTournament` and setting two initial 
indexes being set to search by  `GolfTournament`.`id` or `GolfTournament`.`externalId`

---

## Notes

- `GolfTournament` entity is storing the `startDate` and `endDate` as a `LocalDate` (Not time zone), the reason is that
  a country can have multiple time zones, and the Http Requests Body from source 1 and source are just expecting the
  `countryCode` or `hostCountry`


- On the Data Source 2 example `United States Of America` is not being supporter as Country (ISO 3166), it should be `United States`
  Please see: https://www.oracle.com/java/technologies/javase/jdk8-jre8-suported-locales.html

```json
{
    "tournamentUUID":"87fc6650-e114-4179-9aef-6a9a13030f80",
    "golfCourse":"Happy Days Golf Club",
    "competitionName":"South West Invitational",
    "hostCountry":"United States",
    "epochStart":"1638349200",
    "epochFinish":"1638468000",
    "rounds":"2",
    "playerCount":"35"
}
```

---

## CMD

### Clean, Build and run tests

```
./gradlew clean build test
```

### Run

Start the docker stack, MongoDB and MongoExpress
```
docker-compose -f stack.yml up 
```

Wait for the containers to start. Then, run the application
```
java -jar build/libs/GolfTournament-0.0.1-SNAPSHOT.jar
```

Check, the MongoDB `golf-tournament` DB is being setup: `http://localhost:8081/db/golf-tournament`

Also, you can check if the indexes `_id_` and `externalId` are being setup for the collection `golfTournament` 
`http://localhost:8081/db/golf-tournament/golfTournament`