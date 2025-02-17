db.createUser({
    user: "root",
    pwd: "example",
    roles: [{role: "readWrite", db: "ordernumber_db"}]
});

// Удаляем коллекцию, если она уже существует
db.generatedNumbers.drop();

// Создаем коллекцию и добавляем несколько тестовых документов
db.generatedNumbers.insertMany([
    {
        id: 1,
        orderNumber: "ORD-001",
        createdAt: new Date("2023-01-01T10:00:00"),
        updatedAt: new Date("2023-01-01T10:00:00")
    },
    {
        id: 2,
        orderNumber: "ORD-002",
        createdAt: new Date("2023-01-02T11:00:00"),
        updatedAt: new Date("2023-01-02T11:00:00")
    },
    {
        id: 3,
        orderNumber: "ORD-003",
        createdAt: new Date("2023-01-03T12:00:00"),
        updatedAt: new Date("2023-01-03T12:00:00")
    }
]);
