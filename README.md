# SkyWatch

Referenzprojekt aus Web-Engineering 2 (DHBW): Überwachung erdnaher Asteroiden (Near-Earth Objects).

## Starten

Voraussetzung: Java 21

```bash
./mvnw spring-boot:run
```

Dann http://localhost:8080 öffnen. Eine "Whitelabel Error Page" bedeutet: Die App läuft (es gibt nur noch keine Endpoints).

## Nützliche Links (App muss laufen)

- Swagger UI: http://localhost:8080/swagger-ui.html
- H2-Konsole: http://localhost:8080/h2-console (JDBC URL `jdbc:h2:mem:skywatch`, User `sa`, kein Passwort)

## NASA API-Key

Ohne Einstellung wird der `DEMO_KEY` genutzt (wenige Anfragen pro Stunde).
Eigenen Key kostenlos auf https://api.nasa.gov holen und als Umgebungsvariable setzen, **nie ins Repo committen**:

```bash
NASA_API_KEY=dein_key ./mvnw spring-boot:run
```

In IntelliJ: Run Configuration → Environment variables → `NASA_API_KEY=dein_key`

## Tech-Stack

- Spring Boot 3.4 (Java 21, Maven)
- Spring Data JPA mit H2 (In-Memory-Datenbank)
- Bean Validation, ProblemDetail (RFC 7807) für Fehler
- springdoc / Swagger UI
- NASA NEO API über RestClient, mit Spring Retry und Cache
