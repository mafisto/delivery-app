#!/bin/bash

LQ_USER=${POSTGRES_USER:-postgres}
LQ_PASSWORD=${POSTGRES_PASSWORD:-postgres}
LQ_URL=${POSTGRES_URL:-jdbc:postgresql://postgresql:5432/farel}

/liquibase/liquibase --driver=org.postgresql.Driver --url=$LQ_URL --changeLogFile=./scripts/init.sql --username=$LQ_USER --password=$LQ_PASSWORD update
/liquibase/liquibase --driver=org.postgresql.Driver --url=$LQ_URL --changeLogFile=./scripts/data.sql --username=$LQ_USER --password=$LQ_PASSWORD update
/liquibase/liquibase --driver=org.postgresql.Driver --url=$LQ_URL --changeLogFile=./scripts/changelog-2.xml --username=$LQ_USER --password=$LQ_PASSWORD generate-changelog
