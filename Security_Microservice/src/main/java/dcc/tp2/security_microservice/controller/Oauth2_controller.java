package dcc.tp2.security_microservice.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class Oauth2_controller {

    private AuthenticationManager authenticationManager;
    private JwtEncoder jwtEncoder;
    private JwtDecoder jwtDecoder;
    private UserDetailsService userDetailsService;

    public Oauth2_controller(AuthenticationManager authenticationManager, JwtEncoder jwtEncoder, JwtDecoder jwtDecoder, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestParam String username, @RequestParam String password) {
        // Authentifier l'utilisateur
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        // Générer un token JWT
        Instant instant = Instant.now();

        // Récupérer les rôles de l'utilisateur
        String scope = authenticate.getAuthorities().stream()
                .map(auth -> auth.getAuthority())
                .collect(Collectors.joining(" "));

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuer("MS_sec")
                .subject(authenticate.getName())
                .issuedAt(instant)
                .expiresAt(instant.plus(2, ChronoUnit.MINUTES))
                .claim("name", authenticate.getName())
                .claim("scope", scope)
                .build();

        String accessToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();

        // Créer un refresh token
        JwtClaimsSet refreshTokenClaimsSet = JwtClaimsSet.builder()
                .issuer("MS_sec")
                .subject(authenticate.getName())
                .issuedAt(instant)
                .expiresAt(instant.plus(15, ChronoUnit.MINUTES))
                .claim("name", authenticate.getName())
                .build();
        String refreshToken = jwtEncoder.encode(JwtEncoderParameters.from(refreshTokenClaimsSet)).getTokenValue();

        // Retourner les tokens générés
        Map<String, String> tokens = new HashMap<>();
        tokens.put("Access_Token", accessToken);
        tokens.put("Refresh_Token", refreshToken);

        return tokens;
    }

    @PostMapping("/refreshToken")
    public Map<String, String> refreshToken(@RequestParam String refreshToken) {
        // Décoder le refresh token
        Jwt decoded = jwtDecoder.decode(refreshToken);

        // Récupérer le nom d'utilisateur depuis le refresh token
        String username = decoded.getSubject();

        // Recharger les détails de l'utilisateur
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Générer un nouveau token d'accès
        Instant instant = Instant.now();
        String scope = userDetails.getAuthorities().stream()
                .map(auth -> auth.getAuthority())
                .collect(Collectors.joining(" "));

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuer("MS_sec")
                .subject(userDetails.getUsername())
                .issuedAt(instant)
                .expiresAt(instant.plus(2, ChronoUnit.MINUTES))
                .claim("name", userDetails.getUsername())
                .claim("scope", scope)
                .build();

        String accessToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();

        Map<String, String> tokens = new HashMap<>();
        tokens.put("Access_Token", accessToken);
        tokens.put("Refresh_Token", refreshToken);

        return tokens;
    }
}
