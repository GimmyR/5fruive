# 5Fruive

**5Fruive** is an e-commerce application made with **Spring Boot** and **PostgreSQL**.

## Prerequisites

* **Docker** version **29.0.2** (build **8108357**) or later
* **Docker compose** version **2.40.3** or later

## Environment

You need to have a *.env* file in the root directory of the project with the following content :

```
DB_HOST=db
DB_PORT=5432
DB_USER=admin
DB_PASSWORD=123
DB_NAME=fruive
```

## Build and launch the application

Run the command :

```bash
docker compose up --build
```