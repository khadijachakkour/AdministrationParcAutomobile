package com.example.gatewayservice.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import java.security.interfaces.RSAPublicKey;


@ConfigurationProperties("rsa")
public record RsaConfig(RSAPublicKey publicKey ) {
}


