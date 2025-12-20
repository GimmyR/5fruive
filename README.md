# 5Fruive

**5Fruive** is an e-commerce application made with **Spring Boot** and **PostgreSQL**.

## Prerequisites

**Docker** (version **29.0.2** build **8108357** or later) and **Docker Compose** (version **2.40.3** or later) 
are required to launch the application. 

You can do that by installing [Docker Desktop](https://www.docker.com/products/docker-desktop/).

## Build and launch the application

Run the command :

```bash
docker compose up --build
```

## Feed the database

Run this command to connect to the database :

```bash
docker exec -it postgres-5fruive psql -U admin -d fruive
```

Copy the content of **appdata/database-data.sql** file and paste it there (don't forget to press **Enter**).

Type ``exit`` (and press **Enter**) to quit the database.

