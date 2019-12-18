#!/bin/bash

#Docker command to run the db:
docker volume create pgdata
docker run --name sedi-postgres -e POSTGRES_PASSWORD=sedi@uni1 -d --restart always -p 5432:5432 -v pgdata:/var/lib/postgresql/data sedichile/database
