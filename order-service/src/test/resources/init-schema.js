db.createUser(
    {
        user: "mongodb",
        pwd: "mongodb",
        roles: [
            {
                role: "readWrite",
                db: "farel"
            }
        ]
    }
)
db = db.getSiblingDB('farel');
db.createCollection('order_path');

db.order_path.insertMany([
    { order_id: 1,
        coordinates: [[1,11], [2,22], [3,33], [4,44], [5,55], [6,66], [7,77], [8,88], [9,99], [7,222]]
    },
    { order_id: 2,
        coordinates: [[1.1,11.1], [2.1,22.1], [3.1,33.1], [4.1,44.1], [5.1,55.1], [6.1,66.1], [7.1,77.1], [8,88.1], [9.1,99.1], [7.1,222.1]]
    },
    { order_id: 3,
        coordinates: [[1.11,3.41], [1.1,0.1], [3.31,5.1], [861,44.1], [5.1,33.1], [6.1,10.1], [71.1,77.1], [8,88.1], [9.1,99.1], [7.1,222.1]]
    }
]);