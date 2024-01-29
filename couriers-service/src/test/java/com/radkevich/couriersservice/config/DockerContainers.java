package com.radkevich.couriersservice.config;


import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Objects;

@Testcontainers
public abstract class DockerContainers {

    @Container
    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:16.1")
    ).withInitScript("init.sql")
            .withCreateContainerCmdModifier(cmd -> {
                Objects.requireNonNull(cmd.getHostConfig()).withPortBindings(
                        new PortBinding(Ports.Binding.bindPort(5432),
                                new ExposedPort(5432)));
            });

    @Container
    static final KafkaContainer kafka =
            new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka"))
                    .withCreateContainerCmdModifier(cmd -> {
                        Objects.requireNonNull(cmd.getHostConfig()).withPortBindings(
                                new PortBinding(Ports.Binding.bindPort(9092),
                                        new ExposedPort(9092)),
                                new PortBinding(Ports.Binding.bindPort(9093),
                                        new ExposedPort(9093)),
                                new PortBinding(Ports.Binding.bindPort(2181),
                                        new ExposedPort(2181)));
                    });

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.driver-class-name", postgres::getDriverClassName);
        registry.add("spring.kafka.producer.bootstrap-servers", kafka::getBootstrapServers);

    }


}
