//package com.example.userservice;
//
//
//import com.example.userservice.security.repositories.JpaRegisteredClientRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@SpringBootTest
//public class InsertRegisteredClientTest {
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private JpaRegisteredClientRepository jpaRegisteredClientRepository;
//
//    @Test
//   // @Commit
//    public void insertNewClientToDb() {
////        RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
////                .clientId("client1")
////                .clientSecret(passwordEncoder.encode("pass"))
////                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
////                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
////                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
////                .redirectUri("https://oauth.pstmn.io/v1/callback")
////                .postLogoutRedirectUri("http://127.0.0.1:8080/")
////                .scope(OidcScopes.OPENID)
////                .scope(OidcScopes.PROFILE)
////                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
////                .build();
////
////        jpaRegisteredClientRepository.save(oidcClient);
//    }
//}
