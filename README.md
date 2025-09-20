PetStore CQRS Example (complete v2)
----------------------------------

Modules:
  - common : shared event classes
  - petstore-command-service : write-side Spring Boot service (port 8081)
  - petstore-query-service   : read-side Spring Boot service (port 8082)

To run locally (requires Docker & Maven):
  docker-compose up --build

Endpoints:
  POST http://localhost:8081/pets  -> create pet (body: {"name":"Fido","type":"dog","status":"available"})
  GET  http://localhost:8082/pets  -> list pets (read model)

Notes:
  - Uses Kafka for event delivery. Create Pet on command service; read service consumes events.
  - Dummy Jasypt encryptor bean included for dev convenience.
