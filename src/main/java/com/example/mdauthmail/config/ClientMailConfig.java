package com.example.mdauthmail.config;


import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientMailConfig {

    @Bean
    public ClientSecretCredential clientSecretCredential(@Value("${mail.clientId}") String clientId,
                         @Value("${mail.clientSecret}") String clientSecret,
                         @Value("${mail.tenantId}") String tenantId) {

        return new ClientSecretCredentialBuilder()
            .clientId(clientId)
            .clientSecret(clientSecret)
            .tenantId(tenantId)
            .build();
    }
}
