# Docker Architecture

The application is deployed as separate Docker containers for the frontend, backend, database, and reverse proxy.

```text
                 Browser
                    │
                    ▼
             External Nginx
              :80 / :443
               /       \
              /         \
         /api/*          /*
            │               │
            ▼               ▼
        Backend          Frontend
     Spring Boot :8080   Nginx :80
            │
            ▼
          MySQL
          :3306
```

## Project Structure

```text
web-pcstore/
├── backend/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
│
├── frontend/
│   ├── Dockerfile
│   ├── nginx.conf
│   ├── package.json
│   └── src/
│
├── nginx/
│   ├── nginx.conf
│   └── nginx.local.conf
│
├── docker-compose.yml
├── docker-compose.oracle.yml
└── .env
```

## Dockerfiles

### `backend/Dockerfile`

Builds and runs the Spring Boot backend using Java 8.

### `frontend/Dockerfile`

Builds the React application using Node.js and serves the production build with Nginx.

### `frontend/nginx.conf`

Configures the Nginx server inside the frontend container. It serves the React static files and supports client-side routing.

## Nginx Configuration

There are two Nginx configurations for different environments:

### `nginx/nginx.local.conf`

Used for local development.

It runs without SSL and routes:

```text
/api/* → backend:8080
/*     → frontend:80
```

### `nginx/nginx.conf`

Used for production.

It provides the public entry point, HTTPS/SSL termination, and routes requests to the frontend and backend containers.

## Docker Compose Files

### `docker-compose.yml`

Contains the common application architecture:

* MySQL
* Backend
* Frontend
* Nginx
* persistent MySQL volume

### `docker-compose.oracle.yml`

Contains Oracle/production-specific configuration and overrides, such as production Nginx configuration and SSL certificate mounts.

This separation allows the same application architecture to be used in different environments without duplicating the entire Compose configuration.

## Environment Variables

Database credentials are stored in `.env` and are not committed to Git.

The backend connects to MySQL through the Docker Compose service name:

```text
mysql:3306
```

## Local Run

Start the application with:

```bash
docker compose up -d --build
```

The application is available at:

```text
http://localhost
```

## Production Run

For Oracle deployment:

```bash
docker compose \
  -f docker-compose.yml \
  -f docker-compose.oracle.yml \
  up -d --build
```

## 2CI/CD Pipeline — Feature to Production

Pipeline

```text
feature → PR → dev → CI → PR → main → CI → CD → Oracl
```
Important distinction
```text
PR → CI       = verify changes before merge
push → CI     = verify the updated branch
push → CD     = deploy main to Oracle
```

### Scheme of CI/CD Workflow: Feature → Dev → Main → Oracle

```text
                    ┌──────────────┐
                    │    feature   │
                    └──────┬───────┘
                           │
                           │ push branch
                           ↓
                    ┌──────────────┐
                    │    GitHub    │
                    └──────┬───────┘
                           │
                           │ PR → dev
                           ↓
                    ┌──────────────────┐
                    │       dev        │
                    └────────┬─────────┘
                             │
                             │ CI
                             ↓
                  ┌──────────────────────┐
                  │ CI on dev            │
                  │                      │
                  │ docker-build ✓       │
                  │ ├─ backend           │
                  │ └─ frontend          │
                  └──────────┬───────────┘
                             │
                             │ merge → dev
                             ↓
                    ┌──────────────────┐
                    │       dev        │
                    └────────┬─────────┘
                             │
                             │ PR → main
                             ↓
                    ┌──────────────────┐
                    │       main       │
                    └────────┬─────────┘
                             │
                             │ CI
                             ↓
                  ┌──────────────────────┐
                  │ CI on main           │
                  │                      │
                  │ docker-build ✓       │
                  │ ├─ backend           │
                  │ └─ frontend          │
                  └──────────┬───────────┘
                             │
                             │ merge → main
                             ↓
             ┌────────────────────────────────────────────────────────────┐
             │                 CD                                         │  
             │                                                            │  
             │ SSH → Oracle                                               │    
             │ git pull origin main                                       │  
             │ docker compose -f docker-compose.oracle.yml up -d --build  │            
             └──────────────────┬─────────────────────────────────────────┘
                                │
                                ↓
                         ┌─────────────┐
                         │   Oracle    │
                         │             │
                         │ Application │
                         │      ✓      │
                         └─────────────┘
```


