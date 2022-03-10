FROM postgres:14-alpine3.15

COPY --chown=70:70 ./target/classes/db/migration/ /docker-entrypoint-initdb.d/