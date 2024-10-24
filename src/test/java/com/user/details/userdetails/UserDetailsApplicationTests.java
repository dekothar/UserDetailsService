package com.user.details.userdetails;

import com.user.details.userdetails.security.services.JpaRegisteredClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.UUID;

@SpringBootTest
class UserDetailsApplicationTests {

    @Autowired
    private JpaRegisteredClientRepository jpaRegisteredClientRepository;
    @Test
    void contextLoads() {
        RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("user_details_client")
                .clientSecret("$2a$12$hfI8ApIDk1.sGjpRrkw6PeBBbnxnHr.xDvDgkgt/.HmSzJE.WyHmu")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("http://oauth2/callback/userdetails")
                .postLogoutRedirectUri("http://oauth2/callback/userdetails")
//                .scope(OidcScopes.OPENID)
//                .scope(OidcScopes.PROFILE)
                .scope("ADMIN_DEEP_ROLE")
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();
        jpaRegisteredClientRepository.save(oidcClient);
    }

}
