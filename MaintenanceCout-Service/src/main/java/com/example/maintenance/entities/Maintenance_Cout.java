package com.example.maintenance.entities;

import com.example.maintenance.module.Vehicule;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity
public class Maintenance_Cout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private float cout;  // Le coût de la maintenance
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;  // La date de la maintenance
    private Long id_vehicule;

    @Transient
    private Vehicule vehicule;



}


