# SkyWatch

REST-API mit Spring Boot zur Überwachung erdnaher Asteroiden. Die Daten kommen von der NASA NEO API.

## Starten

Voraussetzung: Java 21

```bash
./mvnw spring-boot:run
```

Die API läuft dann unter http://localhost:8080, die Swagger UI unter http://localhost:8080/swagger-ui.html.

Optional einen eigenen NASA API-Key setzen, sonst wird der `DEMO_KEY` genutzt:

```bash
NASA_API_KEY=dein_key ./mvnw spring-boot:run
```

## Hinweis

Aus Transparenzgründen: Am Ende des Projekts wurde Claude (KI-Assistent von Anthropic) genutzt, um das Repo aufzuräumen, also Kommentare zu vereinheitlichen, den Code einheitlich zu formatieren und dieses README zu überarbeiten.
