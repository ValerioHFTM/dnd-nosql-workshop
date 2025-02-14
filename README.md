# DnD NoSQL Workshop - MongoDB & Quarkus

## Projektbeschreibung
Dieses Projekt demonstriert, wie eine **NoSQL-Datenbank (MongoDB Atlas)** mit einer **Java-Applikation (Quarkus)** verbunden wird.  
Es wurde im Rahmen des **NoSQL-Workshops** entwickelt, um grundlegende CRUD-Operationen auf einer **Cloud-gehosteten MongoDB-Datenbank** durchzuführen.

## Technologien
- **Quarkus** (Java Framework)
- **MongoDB Atlas** (Cloud NoSQL Database)
- **Panache** (ORM für MongoDB)
- **REST API** (CRUD für DnD-Charaktere)

## Features
- **MongoDB Verbindung** in der Cloud
- **CRUD-Operationen** für Charaktere (`GET`, `POST`, `PATCH`, `DELETE`)
- **Lokal oder Cloud-basiert nutzbar**
- **Einfache Erweiterung für z. B. Kampagnen, Inventar, Events**

## API Endpunkte
| Methode | Endpoint | Beschreibung |
|---------|---------|-------------|
| `POST` | `/characters` | Neuen Charakter erstellen |
| `GET` | `/characters` | Alle Charaktere abrufen |
| `GET` | `/characters/{name}` | Charakter nach Name abrufen |
| `PATCH` | `/characters/{name}` | Charakter updaten |
| `DELETE` | `/characters/{name}` | Charakter löschen |

## Dokumentation
1. **MongoDB Atlas einrichten**  
   - Erstellen von **M0 Free Cluster**  
   - Kopieren von **Connection String**  
2. **Quarkus starten**
   ```sh
   ./mvnw quarkus:dev
   ```

3. **API testen**
    ```sh
    curl -X GET http://localhost:8080/characters
    ```

   
## Erkenntnisse & Fazit
- MongoDB eignet sich besonders gut für flexible Datenstrukturen, z. B. Charaktere mit variabler Anzahl an Attributen.
- Mit Quarkus & Panache ist die Integration sehr einfach, aber Referenzen zwischen Collections müssen manuell gepflegt werden.
- Eine Cloud-Lösung wie MongoDB Atlas bietet mehr Flexibilität als eine lokale Datenbank, benötigt aber stabile DNS- und Netzwerkeinstellungen.

