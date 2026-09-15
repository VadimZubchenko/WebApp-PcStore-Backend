
# Architecture

The application consists of separate Docker containers for:

* Frontend
* Backend
* MySQL database
* Nginx reverse proxy

Production and Development run on the same Oracle VM, but they are completely isolated environments with separate containers, Compose projects, configuration, and databases.

```text
                              BROWSER
                                 │
                                 │ HTTPS :443
                                 ▼
                 ┌───────────────────────────────┐
                 │        Production Nginx       │
                 │ webapp-pcstore-backend-nginx-1│          ← Production Nginx
                 │                               │            (External Reverse Proxy)
                 └──────────────┬────────────────┘
                                │
                  ┌─────────────┴─────────────┐
                  │                           │
                  ▼                           ▼
           PRODUCTION                  DEVELOPMENT
      pcstore-manager.uk          dev.pcstore-manager.uk
                  │                           │                  
                  │                           │
          ┌───────┴───────┐           proxy_pass
          │               │         http://dev-nginx:80     ← "dev-nginx" is a network alias
          ▼               ▼                   │               for "pcstore-dev-nginx-1"
      /api/*              /*                  ▼
          │               │         ┌────────────────────┐
          ▼               ▼         │    Dev Nginx       │
      Backend          Frontend     │pcstore-dev-nginx-1 │  ← Development Nginx (Internal Reverse Proxy)
    Spring Boot       React/Nginx   │                    │        
     :8080│               :80       └───────┬────────────┘          
          │                                 │                    
          ▼                         ┌───────┴───────┐               
        MySQL                      /api/*            /*
        pcstore                     │                │
                                    ▼                ▼
                                 Backend          Frontend
                               Spring Boot       React/Nginx
                                  :8080              :80                              
                                    │                                 
                                    ▼
                                  MySQL
                               pcstore_dev
```

## Production Deployment Architecture

The Production environment runs with Production Nginx as the public entry point for `pcstore-manager.uk`.

### Network architecture 

```text
                              BROWSER
                                 │
                                 │ HTTPS :443
                                 ▼
      . . . . . . . . . . . . . . . . . . . . . . . . . . . . .
      .                  PRODUCTION DOCKER NETWORK              .
      .                  webapp-pcstore-backend_default         .
      .                                                         .
      .       ┌──────────────────────────────┐                  .
      .       │ Production Nginx             │                  .
      .       │ webapp-pcstore-backend-      │                  .
      .       │ nginx-1                      │                  .
      .       │ HOST :80 / :443              │                  .
      .       └──────────────┬───────────────┘                  .
      .                      │                                  .
      .             ┌────────┴────────┐                         .
      .             ▼                 ▼                         .
      .      ┌──────────────┐  ┌──────────────┐                 .
      .      │   Backend    │  │   Frontend   │                 .
      .      │ Spring Boot  │  │  Nginx+React │                 .
      .      │    :8080     │  │     :80      │                 .
      .      └──────┬───────┘  └──────────────┘                 .
      .             │                                           .
      .             ▼                                           .
      .      ┌──────────────┐                                   .
      .      │    MySQL     │                                   .
      .      │    :3306     │                                   .
      .      └──────────────┘                                   .
      .                                                         .
      . . . . . . . . . . . . . . . . . . . . . . . . . . . . .
```

### Docker network

All Production containers are connected to:

```text
webapp-pcstore-backend_default
```

The Production Nginx is exposed on the host:

```text
80  → 80
443 → 443
```

### Production routing

```text
/api/* → Backend :8080
/*     → Frontend :80
```

The Frontend container uses its own Nginx to serve React static files.

## Development Deployment Architecture

The Development environment uses the Production Nginx as the public entry point for `dev.pcstore-manager.uk`.

Dev Nginx is connected to the Production Docker network via alias "dev-nginx"
This allows Production Nginx to reach Dev Nginx and forward
requests for `dev.pcstore-manager.uk`. See scheme below.

### Network architecture
```text
                              BROWSER
                                 │
                                 │ HTTPS :443
                                 ▼
                    ┌─────────────────────────┐
                    │   Production Nginx      │
                    │ webapp-pcstore-backend- │
                    │ nginx-1                 │
                    └────────────┬────────────┘
                                 │
                                 │ proxy_pass
                                 │ http://dev-nginx:80
                                 │
      . . . . . . . . . . . . .  │ . . . . . . . . . . . . . . . 
      .       PRODUCTION DOCKER NETWORK                        .
      .       webapp-pcstore-backend_default                   .
      .                                                        .
      .                 ┌─────────────────────────┐            .
      .                 │ Dev Nginx               │            .
      .                 │ pcstore-dev-nginx-1     │            .
      .                 │ alias: dev-nginx        │            .
      .                 │ 8081 → 80               │            .
      .                 │ 8443 → 443              │            .
      .                 └────────────┬────────────┘            .
      .                              │                         .
      . . . . . . . . . . . . . . . .│ . . . . . . . . . . . . .
                                     │
      . . . . . . . . . . . . . . . .│ . . . . . . . . . . . . .
      .       DEVELOPMENT DOCKER NETWORK                       .
      .       pcstore-dev_default                              .
      .                              │                         .
      .             ┌────────────────┼────────────────┐        .
      .             ▼                ▼                ▼        .
      .       ┌────────────┐  ┌────────────┐  ┌────────────    .
      .       │  Backend   │  │  Frontend  │  │   MySQL    │   .
      .       │   :8080    │  │    :80     │  │   :3306    │   .
      .       └────────────┘  └────────────┘  └────────────┘   .
      .                                                        .
      . . . . . . . . . . . . . . . . . . . . . . . . . . . . .
```
### Dev ports

```text
8081 → 80
8443 → 443
```
These provide optional direct access to Dev Nginx. Normal traffic uses:

```text
Production Nginx → dev-nginx:80 → Dev Nginx
```

### Development routing

```text
/api/* → Dev Backend :8080
/*     → Dev Frontend :80
```

The Dev Frontend uses its own Nginx to serve React static files.


## Docker networks

* **Production network:** `webapp-pcstore-backend_default`
* **Development network:** `pcstore-dev_default`
* Dev Nginx is connected to both networks.

`dev-nginx` is the Docker DNS alias for `pcstore-dev-nginx-1`.

