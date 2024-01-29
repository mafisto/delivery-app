graylog = db.getSiblingDB('graylog');
graylog.createUser(
    {
        user: "graylog",
        pwd: "graylog",
        roles: [
            { role: "dbOwner", db: "graylog" }
        ]
    }
);

graylog.graylog.insertOne({"test":1})
graylog.test.insertOne({"test":1})