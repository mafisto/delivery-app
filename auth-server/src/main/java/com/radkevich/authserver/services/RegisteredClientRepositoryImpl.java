package com.radkevich.authserver.services;

import com.radkevich.authserver.repository.PersonsRepository;
import com.radkevich.authserver.repository.entity.PersonsEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisteredClientRepositoryImpl implements RegisteredClientRepository {

    private final PersonsRepository personsRepository;

    @Override
    public void save(RegisteredClient registeredClient) {

    }

    public RegisteredClient findById(String id) {
        PersonsEntity entity = personsRepository.findById(UUID.fromString(id)).get();
        RegisteredClient registeredClient = convertToRegisteredClient(entity);
        return registeredClient;
    }
    public RegisteredClient findByClientId(String clientId) {
        PersonsEntity entity = personsRepository.findByUsername(clientId);
        RegisteredClient registeredClient = convertToRegisteredClient(entity);
        return registeredClient;
    }

    private static RegisteredClient convertToRegisteredClient(PersonsEntity user) {
        if (user == null){
            return null;
        }
        return RegisteredClient.withId(user.getId().toString())
                .clientName(user.getUsername())
                .clientId(user.getUsername())
                .clientSecret("{noop}" + user.getPassword())
                .scope(user.getRoleId().getRole())
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofMinutes(60))
                        .refreshTokenTimeToLive(Duration.ofHours(5))
                        .build())
                .build();
    }
}
