package com.example.maintenance.client;

import com.example.maintenance.module.Vehicule;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "vehicule-service", url = "http://localhost:8085")
public interface VehiculeRestFeign {

    @GetMapping("/api/v1/vehicules/{id}")
    @CircuitBreaker(name = "vehiculeService", fallbackMethod = "fallbackVehiculeById")
    @Retry(name = "vehiculeServiceRetry")
    @RateLimiter(name = "vehiculeServiceRateLimiter", fallbackMethod = "rateLimiterFallbackVehiculeById")
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

    // Méthode de secours (fallback) spécifique au Rate Limiter
    default Vehicule rateLimiterFallbackVehiculeById(Long id, Throwable throwable) {
        return new Vehicule(
                id,
                "Rate Limiter - Véhicule Indisponible",
                "Veuillez réessayer",
                "Rate Limited",
                "Non spécifiée",
                null,
                null,
                Vehicule.Statut.RESERVE
        );
    }

}
