# Deployment and CI/CD
## Dockerfiles

The project uses separate Dockerfiles for the backend and frontend.

| File                  | Purpose                                                                                |
| --------------------- | -------------------------------------------------------------------------------------- |
| `backend/Dockerfile`  | Builds and runs the Spring Boot application on port `8080`.                            |
| `frontend/Dockerfile` | Builds the React application and serves the production build using Nginx on port `80`. |
| `frontend/nginx.conf` | Configures Nginx to serve React static files and support client-side routing.          |

---

## Nginx Configuration

The project uses different Nginx configurations for local and Oracle deployments.

### Local

```text
nginx/nginx.local.conf
```

Used for local development.

Application:

```text
http://localhost
```

### Oracle

```text
nginx/nginx.conf
```

Used for both Development and Production deployments on the Oracle VM.

It provides:

* HTTPS
* SSL certificates
* reverse proxy routing
* frontend routing
* backend API routing

---

## Deployment Environments

The application can run locally and on the Oracle VM.
* Production and Development run on the same Oracle VM using separate containers, Compose projects, configuration, and databases.

| Environment | Branch | Compose project | Domain                           | Host ports     | Database      |
| ----------- | ------ | --------------- | -------------------------------- | -------------- | ------------- |
| Local       | any    | default         | `http://localhost`               | `80`           | local         |
| Development | `dev`  | `pcstore-dev`   | `https://dev.pcstore-manager.uk` | `8081`, `8443` | `pcstore_dev` |
| Production  | `main` | default         | `https://pcstore-manager.uk`     | `80`, `443`    | `pcstore`     |

* Development ports `8081` and `8443` provide **direct access to the Dev Nginx container**. Normal Development traffic does not use these ports:
```text
Browser
   │
   │ HTTPS :443
   ▼
Production Nginx
   │
   │ proxy_pass http://dev-nginx:80
   ▼
Dev Nginx
```

The Development ports can be used for direct access when needed:

```text
http://<Oracle-host>:8081
https://<Oracle-host>:8443
```

Production uses ports `80` and `443` as the public entry point.

Production Nginx is also the public entry point for the Development domain.

---

## Local Deployment

Start the application locally:

```bash
docker compose up -d --build
```

Check the containers:

```bash
docker compose ps
```

The application is available at:

```text
http://localhost
```

The local environment uses:

```text
docker-compose.yml
```

---

## Oracle Deployment

The Oracle VM contains separate application directories:

```text
Production:
~/WebApp-PcStore-Backend

Development:
~/WebApp-PcStore-Backend-dev
```

The corresponding branches are:

```text
main → Production
dev  → Development
```

Oracle deployments use:

```text
docker-compose.oracle.yml
```

This configuration contains Oracle-specific settings such as:

* host port mappings
* SSL certificate mounts
* Oracle Nginx configuration
* Docker network configuration

---

## Development Deployment

Development uses the Compose project:

```text
pcstore-dev
```

and the environment file:

```text
.env.dev
```

Example configuration:

```env
MYSQL_DATABASE=pcstore_dev
MYSQL_USER=vadimzu_dev
MYSQL_PASSWORD=...

NGINX_HTTP_PORT=8081
NGINX_HTTPS_PORT=8443
```

The `.env.dev` file is not committed to Git.

Deploy Development:

```bash
cd ~/WebApp-PcStore-Backend-dev

git pull --ff-only origin dev

docker compose \
  -p pcstore-dev \
  --env-file .env.dev \
  -f docker-compose.oracle.yml \
  up -d --build
```

Check the deployment:

```bash
docker compose -p pcstore-dev ps
```

Expected containers:

```text
pcstore-dev-nginx-1
pcstore-dev-frontend-1
pcstore-dev-backend-1
pcstore-dev-mysql-1
```

Development ports:

```text
8081 → Nginx :80
8443 → Nginx :443
```

The application is available at:

```text
https://dev.pcstore-manager.uk
```

---

## Production Deployment

Production uses the default Compose project and the `main` branch.

Environment:

```text
.env
```
Example configuration:

```env
MYSQL_DATABASE=pcstore
MYSQL_USER=vadimzu
MYSQL_PASSWORD=...

NGINX_HTTP_PORT=80
NGINX_HTTPS_PORT=443
```

Deploy Production:

```bash
cd ~/WebApp-PcStore-Backend

git pull --ff-only origin main

docker compose \
  -f docker-compose.oracle.yml \
  up -d --build
```

Check the deployment:

```bash
docker compose ps
```

Production ports:

```text
80  → Nginx :80
443 → Nginx :443
```

The application is available at:

```text
https://pcstore-manager.uk
```

---

# CI/CD Pipeline

The deployment flow is:

```text
feature
   │
   │ Pull Request
   ▼
  dev
   │
   │ CI + CD
   ▼
Oracle — Development
   │
   │ testing
   │
   │ Pull Request
   ▼
 main
   │
   │ CI + CD
   ▼
Oracle — Production
```

### CI

CI verifies that the Docker images can be built successfully.

The pipeline builds:

```text
Backend
Frontend
```

CI runs for Pull Requests and branch updates according to the GitHub Actions workflow configuration.

### CD

CD deploys the corresponding branch to the Oracle VM:

```text
dev  → ~/WebApp-PcStore-Backend-dev
main → ~/WebApp-PcStore-Backend
```

The deployment server updates the repository using:

```bash
git pull --ff-only origin dev
```

or:

```bash
git pull --ff-only origin main
```

`--ff-only` prevents Git from creating a merge commit on the deployment server.

The deployment then starts the corresponding Docker Compose environment.

---

# Git Workflow

The project uses the following branch structure:

```text
feature → dev → main
```

Feature branches are used for new functionality and fixes.

Create a feature branch:

```bash
git switch -c feature/update-dashboard
```

Push the branch:

```bash
git push -u origin feature/update-dashboard
```

Create a Pull Request:

```text
feature/update-dashboard → dev
```

After Development testing, create:

```text
dev → main
```

---

# GitHub Secrets

GitHub Actions uses the following repository secrets for SSH access to the Oracle VM:

```text
ORACLE_HOST
ORACLE_USER
ORACLE_SSH_KEY
```

The same SSH credentials are used for both Development and Production.

Sensitive values are stored in GitHub Secrets and are not committed to the repository.

# More detail CI/CD Pipeline 

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
     ┌──────────────────────┐
     │ CI on PR             │
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
                │ push / merge
                ↓
     ┌──────────────────────┐
     │ CI on dev             │
     │                       │
     │ docker-build ✓        │
     │ ├─ backend            │
     │ └─ frontend            │
     └──────────┬────────────┘
                │
                │ CD
                ↓
┌────────────────────────────────────────────────────────────┐
│                 CD — Development                           │
│                                                            │
│ SSH → Oracle                                               │
│ git pull --ff-only origin dev                              │
│ docker compose -p pcstore-dev                              │
│   --env-file .env.dev                                      │
│   -f docker-compose.oracle.yml up -d --build               │
└──────────────────┬─────────────────────────────────────────┘
                   │
                   ↓
       ┌──────────────────────┐
       │ Oracle — DEV         │
       │                      │
       │ dev.pcstore-         │
       │ manager.uk           │
       │                      │
       │ Application ✓        │
       └──────────┬───────────┘
                  │
                  │ testing
                  ↓
       ┌──────────────────┐
       │       dev        │
       └────────┬─────────┘
                │
                │ PR → main
                ↓
     ┌──────────────────────┐
     │ CI on PR             │
     │                      │
     │ docker-build ✓       │
     │ ├─ backend           │
     │ └─ frontend          │
     └──────────┬───────────┘
                │
                │ merge → main
                ↓
       ┌──────────────────┐
       │       main       │
       └────────┬─────────┘
                │
                │ push / merge
                ↓
     ┌──────────────────────┐
     │ CI on main            │
     │                       │
     │ docker-build ✓        │
     │ ├─ backend            │
     │ └─ frontend            │
     └──────────┬────────────┘
                │
                │ CD
                ↓
┌────────────────────────────────────────────────────────────┐
│                 CD — Production                            │
│                                                            │
│ SSH → Oracle                                               │
│ git pull --ff-only origin main                             │
│ docker compose -f docker-compose.oracle.yml                │
│   up -d --build                                            │
└──────────────────┬─────────────────────────────────────────┘
                   │
                   ↓
       ┌──────────────────────┐
       │ Oracle — PRODUCTION  │
       │                      │
       │ pcstore-manager.uk   │
       │                      │
       │ Application ✓        │
       └──────────────────────┘
```