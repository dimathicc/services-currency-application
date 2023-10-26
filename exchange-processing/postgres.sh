#!/bin/zsh
docker run --name pg-13.3 -p 5432:5432 \
    -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres \
    -v /srv/db/postgres:/var/lib/postgresql/data:rw \
    -d postgres:13.3