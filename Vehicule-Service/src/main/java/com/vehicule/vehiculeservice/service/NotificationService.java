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
    public void sendUserNotification(String message) {
        String url = "http://localhost:5000/notifications";

        // Configurez l'en-tête
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String payload = "{\"message\": \"" + message + "\"}";


        // Préparez la requête
        HttpEntity<String> request = new HttpEntity<>(payload, headers);

        // Envoyez la requête
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        System.out.println("Notification response: " + response.getBody());
    }

}
