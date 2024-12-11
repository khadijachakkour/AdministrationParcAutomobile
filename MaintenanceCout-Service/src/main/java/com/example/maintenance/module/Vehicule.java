package com.example.maintenance.module;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Vehicule {

    private Long id;

    private String marque;

    private String modele;


    private String typeVehicule;

    private String couleur;
    private LocalDate dateDerniereMaintenance;
    private LocalDate dateAchat;


    @Enumerated(EnumType.STRING)
    private Statut statut;


    public enum Statut {
        DISPONIBLE,
        EN_MAINTENANCE,
        RESERVE
    }
}
