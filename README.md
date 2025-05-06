Thought for a second



# Meeting Manager

A microservices-based meeting coordination app built with Spring Boot, Docker Compose, and React.  
Each “employee” runs in its own container/port, subscribing over STOMP/WebSocket to real-time meeting invitations from a central orchestrator.

---

## Architecture

- **Central** (`meeting-manager-central`):  
  - Spring Boot REST API & WebSocket broker  
  - Stores meetings & invites in PostgreSQL  
  - Broadcasts new meetings to all employees via STOMP `/topic/meetings`  
  - Sends per-user invites via STOMP `/user/{name}/queue/invites`

- **Employees** (`meeting-manager-employee`):  
  - Five identical Spring Boot apps (Alice, Bob, Charlie, David, Eve)  
  - Each listens on its own port (8081–8085)  
  - Connects to Central’s WebSocket, handles user-specific `/queue/invites` messages  

- **Frontend** (`meeting-manager-frontend`):  
  - React app served on port 3000  
  - Fetches meeting list from Central’s REST API  
  - Subscribes to `/topic/meetings` for live updates  
  - Offers a form to create new meetings  

- **PostgreSQL** (`postgres:15`): persisting meetings & invites  
- **Redis** (`redis:7`): optional pub/sub broker  

---

## Getting Started

### Prerequisites

- Docker & Docker Compose installed  
- (Optional) Docker Hub account for pulling pre-built images  

### Local Development (Source Builds)

1. **Clone repository**  
   ```bash
   git clone https://github.com/bpvacar/meeting-manager.git
   cd meeting-manager
````

2. **Build & run everything**

   ```bash
   docker compose up --build
   ```

3. **Open the React UI**
   [http://localhost:3000](http://localhost:3000)

4. **Inspect services**

   ```bash
   docker compose ps
   ```

---

## Using the API

All Central endpoints are under `/api/meetings`.

* **List meetings**

  ```bash
  curl http://localhost:8080/api/meetings
  ```

* **Create a meeting**

  ```bash
  curl -X POST http://localhost:8080/api/meetings \
    -H "Content-Type: application/json" \
    -d '{
      "topic":"Team Sync",
      "startTime":"2025-05-07T10:00:00Z",
      "durationMinutes":30
    }'
  ```

* **Invite an employee**

  ```bash
  curl -X POST http://localhost:8080/api/meetings/1/invites \
    -H "Content-Type: application/json" \
    -d '{ "employeeName":"Alice" }'
  ```

---

## Docker Hub Deployment

If you’ve pushed images to Docker Hub:

1. **Edit `docker-compose.yml`** to use your Hub images:

   ```yaml
   central:
     image: bpvacar/meeting-manager-central:latest
   employee1:
     image: bpvacar/meeting-manager-employee:latest
   # …
   frontend:
     image: bpvacar/meeting-manager-frontend:latest
   ```
2. **Pull & run**

   ```bash
   docker compose pull
   docker compose up -d
   ```

---

## Project Structure

```
.
├── central/          # Spring Boot central orchestrator
│   ├── src/main/java/com/example/central
│   └── Dockerfile.central
├── employee/         # Spring Boot employee instance
│   ├── src/main/java/com/example/employee
│   └── Dockerfile.employee
├── frontend/         # React UI
│   ├── src/
│   ├── public/
│   └── Dockerfile
└── docker-compose.yml
```

---

## Contributing

1. Fork & clone
2. Create a feature branch
3. Submit a Pull Request

---



