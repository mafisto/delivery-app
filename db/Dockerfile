FROM liquibase/liquibase:4.25

COPY ./db/mongo/lib /liquibase/lib
COPY ./db/mongo/changelog /liquibase/mongo/changelog
COPY ./db/postgres/changelog /liquibase/postgres/changelog
COPY ./db/liquibase/run-liquibase.sh /liquibase/run-liquibase.sh
