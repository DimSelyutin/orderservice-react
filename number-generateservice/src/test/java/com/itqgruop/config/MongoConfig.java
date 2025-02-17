package com.itqgruop.config;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import lombok.extern.slf4j.Slf4j;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

/**
 * Конфигурационный файл для поднятия контейнера MongoDB перед тестами.
 */
@Slf4j
@ActiveProfiles("test")
@Testcontainers
public abstract class MongoConfig {
    @Container
    public static final GenericContainer<?> MONGO_DB_CONTAINER = new GenericContainer<>(
            DockerImageName.parse("mongo:6.0.7"))
            .withExposedPorts(27017)
            .withEnv("MONGO_INITDB_ROOT_USERNAME", "root") // Установка имени пользователя
            .withEnv("MONGO_INITDB_ROOT_PASSWORD", "example") // Установка пароля
            .withCopyFileToContainer(MountableFile.forClasspathResource("init-schema.js"), "/docker-entrypoint-initdb.d/init-script.js");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        // Используем динамический порт контейнера
        registry.add("spring.data.mongodb.uri", () ->
                "mongodb://root:example@localhost:" + MONGO_DB_CONTAINER.getFirstMappedPort() + "/ordernumber_db?authSource=admin"
        );
    }
}
