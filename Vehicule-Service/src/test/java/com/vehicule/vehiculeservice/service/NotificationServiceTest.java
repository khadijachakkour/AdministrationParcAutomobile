package com.vehicule.vehiculeservice.service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

class NotificationServiceTest {

    @Test
    void shouldSendUserNotification() {
        // Arrange
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        NotificationService notificationService = new NotificationService(restTemplate);

        String url = "http://localhost:5000/notifications";
        String message = "Hello, this is a test notification!";
        String payload = "{\"message\": \"" + message + "\"}";

        ResponseEntity<String> mockResponse = ResponseEntity.ok("Notification sent successfully");
        Mockito.when(restTemplate.postForEntity(eq(url), any(HttpEntity.class), eq(String.class)))
                .thenReturn(mockResponse);

        // Act
        notificationService.sendUserNotification(message);

        // Assert
        ArgumentCaptor<HttpEntity<String>> captor = ArgumentCaptor.forClass(HttpEntity.class);
        Mockito.verify(restTemplate).postForEntity(eq(url), captor.capture(), eq(String.class));

        HttpEntity<String> capturedRequest = captor.getValue();
        assertEquals(payload, capturedRequest.getBody());
        assertEquals(MediaType.APPLICATION_JSON, capturedRequest.getHeaders().getContentType());
    }
}
