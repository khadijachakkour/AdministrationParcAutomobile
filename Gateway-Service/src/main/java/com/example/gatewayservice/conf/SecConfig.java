package com.example.gatewayservice.conf;


import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
@Configuration
@EnableWebFluxSecurity
//@EnableWebSecurity
public class SecConfig {

  private RsaConfig rsaConfig ;

  public SecConfig(RsaConfig rsaConfig) {
    this.rsaConfig = rsaConfig;
  }

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
    httpSecurity
            .csrf(csrf -> csrf.disable()) // Désactivation de CSRF pour simplifier
            .authorizeExchange(auth -> auth
                            .pathMatchers("/User_Service/api/users/email/{id}").permitAll()  // Validation de l'email, public
                            .pathMatchers("/User_Service/api/users/email").permitAll()  // Vérification de l'email public
                            .pathMatchers("/User_Service/api/users/**").hasAuthority("ADMIN")  // Création d'un utilisateur, réservé à ADMIN
                            //.pathMatchers("/User_Service/api/users/{id}").hasAnyAuthority("ADMIN", "USER")  // Accès à un utilisateur par ID, réservé à ADMIN et USER
                                    // Accès à un utilisateur par ID pour les ADMIN et USER
                    .anyExchange().authenticated() // Exige une authentification pour toute autre route
            )
            .oauth2ResourceServer(oauth2 -> oauth2.jwt()); // Utilisation de JWT pour l'authentification
    return httpSecurity.build();
  }
  @Bean
  public ReactiveJwtDecoder jwtDecoder() {
    // Ensure that rsaConfig.publicKey() returns a valid PublicKey
    return NimbusReactiveJwtDecoder.withPublicKey(rsaConfig.publicKey()).build();
  }
}
