package com.vehicule.vehiculeservice.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {

    private final RestTemplate restTemplate;

    public NotificationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void envoyerNotification(String message) {
        String url = "http://localhost:5000/notify"; // URL du service Flask

        // Préparer les en-têtes et le corps de la requête
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String payload = "{\"message\": \"" + message + "\"}";
        HttpEntity<String> request = new HttpEntity<>(payload, headers);

        try {
            // Envoyer une requête POST au service Flask
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            System.out.println("Réponse du service Flask : " + response.getBody());
        } catch (Exception e) {
            System.err.println("Erreur lors de l'envoi de la notification : " + e.getMessage());
        }
    }
}
