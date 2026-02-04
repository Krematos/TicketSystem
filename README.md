# TicketSystem

Jednoduchý a efektivní systém pro správu fronty ticketů postavený na moderních Javových technologiích. Tento projekt slouží jako ukázka implementace REST API s důrazem na čistý kód a jednoduchost.

## 🛠 Technologie

Projekt využívá nejnovější standardy pro zajištění výkonu a udržitelnosti:

*   **Java 21**: Využití moderních features jazyka (Records, synchronized, atd.).
*   **Spring Boot 3.5.8**: Robustní framework pro rychlý vývoj backendu.
*   **Lombok**: Minimalizace boilerplate kódu (anotace `@Data`, `@RequiredArgsConstructor`).
*   **Maven**: Správa závislostí a build proces.

## 🚀 Funkcionalita

Aplikace poskytuje REST API pro správu FIFO (First-In-First-Out) fronty:

1.  **Vytvoření ticketu**: Zákazník si vygeneruje nový ticket a zařadí se na konec fronty.
2.  **Zobrazení aktuálního ticketu**: Obsluha vidí, kdo je aktuálně na řadě.
3.  **Odbavení ticketu**: Odebrání obslouženého ticketu z fronty.

Systém běží **in-memory**, což zajišťuje maximální rychlost odezvy pro účely demonstrace.

## 📡 API Endpoints

Server běží standardně na portu `8080`.

| Metoda | Endpoint | Popis |
| :--- | :--- | :--- |
| `POST` | `/tickets` | Vytvoří nový ticket. Vrací ID, čas vytvoření a pozici ve frontě. |
| `GET` | `/tickets/current` | Vrátí ticket, který je aktuálně na řadě (první ve frontě). |
| `DELETE` | `/tickets/current` | Odstraní aktuální ticket z fronty (vyřešeno). |

**Příklady cURL:**

1.  **Vytvoření ticketu:**
    ```bash
    curl -X POST http://localhost:8080/tickets
    ```

2.  **Získání aktuálního ticketu:**
    ```bash
    curl -X GET http://localhost:8080/tickets/current
    ```

3.  **Smazání aktuálního ticketu:**
    ```bash
    curl -X DELETE http://localhost:8080/tickets/current
    ```

**Příklad odpovědi (`POST /tickets`):**
```json
{
  "id": 1245,
  "createdAt": "2024-02-04 19:30",
  "positionInQueue": 0
}
```

## 🏁 Jak spustit

Pro spuštění aplikace stačí mít nainstalovanou Javu 21 a Maven.

```bash
mvn spring-boot:run
```

Aplikace bude dostupná na `http://localhost:8080`.

Projekt obsahuje integrační test, který ověřuje základní funkčnost API. Testy lze spustit pomocí:

```bash
mvn test
```

## 💡 Možná budoucí vylepšení

Pro nasazení do produkčního prostředí by bylo vhodné zvážit:

*   **Persistence**: Nahrazení in-memory `LinkedList` databází (PostgreSQL/H2) pro zachování dat po restartu.
*   **Websocket / SSE**: Pro real-time aktualizaci pozice ve frontě na klientovi.
*   **Docker**: Kontejnerizace pro snazší nasazení.
*   **Swagger UI**: Přidání `springdoc-openapi` pro interaktivní dokumentaci API.



