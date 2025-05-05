# Meeting Manager

Este proyecto es una aplicación de gestión de reuniones con **frontend** en React y **backend** en Spring Boot, orquestado mediante Docker Compose.

## Estructura del repositorio

```
meeting-manager/           # Raíz del proyecto
├── README.md              # Documentación
├── docker-compose.yml     # Orquestación de servicios
├── backend/               # Carpeta con el código Java/Spring Boot
│   ├── Dockerfile
│   └── src/...
└── frontend/              # Carpeta con el código React
    ├── Dockerfile
    └── src/...
```

---

## Prerrequisitos

* [Docker](https://www.docker.com/get-started) instalado.
* [Docker Compose](https://docs.docker.com/compose/install/) (opcional si usas `docker compose`).

---

## Ejecución local con Docker Compose

1. Clona el repositorio:

   ```bash
   git clone https://github.com/bpvacar/meeting-manager.git
   cd meeting-manager
   ```

2. Construye y levanta los servicios:

   ```bash
   # Con Docker Compose V2:
   docker compose up --build -d

   # O con Docker Compose V1:
   docker-compose up --build -d
   ```

3. Accede a:

    * **Frontend:** [http://localhost:3000](http://localhost:3000)
    * **API (backend):** [http://localhost:8080/api/meetings](http://localhost:8080/api/meetings)

---

## Uso de imágenes desde Docker Hub

Si prefieres no construir localmente, puedes usar las imágenes publicadas en Docker Hub:

1. Descarga las imágenes:

   ```bash
   docker pull benjamin6942/meeting-manager-backend:latest
   docker pull benjamin6942/meeting-manager-frontend:latest
   ```

2. Modifica `docker-compose.yml` para usar las imágenes (ya publicado en la rama `main`).

3. Levanta los servicios:

   ```bash
   docker compose up -d
   ```

---

## Limpieza

Para detener y eliminar contenedores, redes y volúmenes:

```bash
docker compose down -v
```
