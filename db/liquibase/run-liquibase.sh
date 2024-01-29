#!/bin/bash

/liquibase/liquibase --driver=liquibase.ext.mongodb.database.MongoClientDriver --url=${MONGO_URL} --changeLogFile=./mongo/changelog/db.mongo.changelog-master.xml --username=${MONGO_USER} --password=${MONGO_PASSWORD}  update
/liquibase/liquibase --driver=org.postgresql.Driver --url=${POSTGRES_URL} --changeLogFile=./postgres/changelog/db.postgres.changelog-master.xml --username=${POSTGRES_USER} --password=${POSTGRES_PASSWORD} update


#/liquibase/liquibase --driver=org.postgresql.Driver --url=$LQ_URL --changeLogFile=./changelog/db.changelog-master.xml --output-file=./changelog/generated-upgrade-script.sql --username=$LQ_USER --password=$LQ_PASSWORD updateSQL
