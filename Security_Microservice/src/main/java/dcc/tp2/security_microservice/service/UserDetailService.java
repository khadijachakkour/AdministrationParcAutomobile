package dcc.tp2.security_microservice.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class UserDetailService implements UserDetailsService {
/*
    // Simuler une base de données d'utilisateurs pour le test
    // Dans une vraie application, vous pouvez utiliser un repository pour récupérer les utilisateurs depuis la base de données.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Ici, nous avons un exemple de récupération d'utilisateur. Vous pouvez remplacer cette logique par votre propre logique.
        if ("admin".equals(username)) {
            return User.withUsername("admin")
                    .password("{noop}admin123") // 'noop' est un encodeur de mot de passe sans cryptage, mais dans une application réelle, il est préférable d'utiliser un encodeur sécurisé.
                    .authorities("ROLE_ADMIN")
                    .build();
        } else if ("user".equals(username)) {
            return User.withUsername("user")
                    .password("{noop}user123")
                    .authorities("ROLE_USER")
                    .build();
        } else {
            throw new UsernameNotFoundException("Utilisateur non trouvé");
        }
    }*/

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Construire l'URL pour récupérer les données utilisateur
        String url = "http://localhost:8090/api/users/email/" + username;

        // Effectuer la requête GET pour récupérer les données sous forme de Map
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> userData = response.getBody();

            if (userData != null) {
                // Extraire les informations de la réponse (email, mot de passe, rôles)
                String email = (String) userData.get("email");
                String password = (String) userData.get("password");

                // Ajouter le préfixe {noop} au mot de passe pour indiquer qu'il est en texte clair
                password = "{noop}" + password;

                // Extraire les rôles
                @SuppressWarnings("unchecked")
                List<String> roles = (List<String>) userData.get("roles");

                // Créer et retourner un objet UserDetails avec les informations obtenues
                return User.withUsername(email)
                        .password(password) // Le mot de passe avec le préfixe {noop}
                        .authorities(roles.toArray(new String[0]))
                        .build();
            }
        }

        // Si l'utilisateur n'est pas trouvé, lancer une exception
        throw new UsernameNotFoundException("Utilisateur non trouvé");
    }

}
