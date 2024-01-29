package com.radkevich.oderservice.config;


import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.startupcheck.MinimumDurationRunningStartupCheckStrategy;
import org.testcontainers.containers.wait.strategy.WaitStrategy;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

import java.time.Duration;
import java.util.Objects;

@Testcontainers
public abstract class DockerContainers {


    @Container
    static final GenericContainer<?> mongoDBContainer = new GenericContainer(
            DockerImageName.parse("mongo:6.0.7"))
            .withEnv("MONGO_INITDB_DATABASE","farel")
            .withEnv("MONGO_INIT_ROOT_USERNAME","mongodb")
            .withEnv("MONGO_INIT_ROOT_PASSWORD","mongodb")
            .withExposedPorts(27017)
            .withStartupCheckStrategy(
                    new MinimumDurationRunningStartupCheckStrategy(Duration.ofSeconds(1))
            )
            .withCopyFileToContainer(MountableFile.forClasspathResource("init-schema.js"),
                    "/docker-entrypoint-initdb.d/init-schema.js");

    @Container
    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:16.1")
    ).withInitScript("init.sql")
            .withCreateContainerCmdModifier(cmd -> {
                Objects.requireNonNull(cmd.getHostConfig()).withPortBindings(
                        new PortBinding(Ports.Binding.bindPort(5432),
                                new ExposedPort(5432)));});

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.driver-class-name", postgres::getDriverClassName);

        registry.add("spring.data.mongodb.host", mongoDBContainer::getHost);
        registry.add("spring.data.mongodb.port", mongoDBContainer::getFirstMappedPort);
        registry.add("spring.data.mongodb.username", () -> "mongodb");
        registry.add("spring.data.mongodb.password", () -> "mongodb");
//        registry.add("spring.data.mongodb.database", () -> "farel");
        registry.add("spring.data.mongodb.authentication-database", () -> "farel");

    }

}
