package com.example.maintenance.client;

import com.example.maintenance.module.Vehicule;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "vehicule-service", url = "http://localhost:8085")
public interface VehiculeRestFeign {

    @GetMapping("/api/v1/vehicules/{id}")
    @CircuitBreaker(name = "vehiculeService", fallbackMethod = "fallbackVehiculeById")
    @Retry(name = "vehiculeServiceRetry")
    @RateLimiter(name = "vehiculeServiceRateLimiter")
    @Bulkhead(name = "vehiculeBulkhead")
    Vehicule VehiculeById(@PathVariable("id") Long id);

    // Méthode de secours (fallback) pour le circuit breaker
    default Vehicule fallbackVehiculeById(Long id, Throwable throwable) {
        return new Vehicule(
                id,
                "Inconnue",
                "Indisponible",
                "Non spécifié",
                "Non spécifiée",
                null,
                null,
                Vehicule.Statut.EN_MAINTENANCE
        );
    }


    @PutMapping("/api/v1/vehicules/{id}/statut")
    ResponseEntity<Vehicule> updateStatut(
            @PathVariable("id") Long id,
            @RequestBody Vehicule.Statut statut
    );
}

