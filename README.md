# 5Fruive

**5Fruive** is an e-commerce application made with **Spring Boot** and **PostgreSQL**.

## Prerequisites

* **Docker** version **29.0.2** (build **8108357**) or later
* **Docker compose** version **2.40.3** or later

## Feed the database

Run this command to run only database :

```bash
docker compose up db -d
```

Then, run this command to connect to the database :

```bash
docker exec -it postgres-5fruive psql -U admin -d fruive
```

Copy the content of **appdata/database-data.sql** file and paste it there (don't forget to press **Enter**).

Type ``exit`` (and press **Enter**) to quit the database.

Shutdown the database by running this command :

```bash
docker compose down db
```

## Build and launch the application

Run the command :

```bash
docker compose up --build
```