# Mini-Aufgaben Web-Engineering 2

## 1. Wie funktioniert das Internet?

Befehl:

```bash
curl -v https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY 2>&1 | head -30
```

| Frage | Antwort | Wo im curl-Output? |
|---|---|---|
| Welche IP wird aufgelöst? | Eine IP von api.nasa.gov (z. B. `52.0.14.116`, kann je nach Zeitpunkt wechseln) | Zeile `* Trying <IP>:443...` |
| Welcher Port? | **443** (HTTPS) | `* Connected to api.nasa.gov (<IP>) port 443` |
| Welcher Statuscode? | **200 OK** (bei zu vielen Anfragen mit DEMO_KEY: 429 Too Many Requests) | `< HTTP/2 200` |
| Welcher Content-Type? | **application/json** | `< content-type: application/json` |

Hinweis: Zeilen mit `>` sind der Request (Client → Server), Zeilen mit `<` die Response (Server → Client), Zeilen mit `*` Verbindungsinfos (DNS, TCP, TLS).

## 2. Moderne Webentwicklung

- [x] Spring Boot Projekt erstellt (Maven, Java 21, Spring Boot 3.4.x, Group `de.dhbw`, Artifact `skywatch`, Dependency Spring Web)
- [ ] Lokal starten mit `./mvnw spring-boot:run` und http://localhost:8080 öffnen → "Whitelabel Error Page" = funktioniert

### Entitäten für eine eigene Domäne (Beispiel: Trainingsplan)

| Entität | Attribute |
|---|---|
| **Exercise** (Übung) | id, name, muscleGroup, equipment |
| **Workout** (Trainingseinheit) | id, date, durationMinutes, notes |
| **WorkoutSet** (Satz) | id, workout → Workout, exercise → Exercise, reps, weightKg |
| **TrainingPlan** (Trainingsplan) | id, name, daysPerWeek, goal |

Beziehungen: Ein Workout hat viele Sätze; jeder Satz gehört zu genau einer Übung. Ein Trainingsplan enthält mehrere Übungen.

## 3. Git und Branching

Feature-Branch `feature/initial-setup` → Pull Request auf GitHub → Merge in `main`.
