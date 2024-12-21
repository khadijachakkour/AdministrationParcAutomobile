package com.vehicule.vehiculeservice.web;

import com.vehicule.vehiculeservice.entities.Vehicule;
import com.vehicule.vehiculeservice.service.NotificationService;
import com.vehicule.vehiculeservice.service.VehiculeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@ExtendWith(MockitoExtension.class)
class APITest {

    @Mock
    private VehiculeService vehiculeService;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private API api;


    private MockMvc mockMvc;
    private List<Vehicule> vehiculeList;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(api).build();
        vehiculeList = List.of(
                new Vehicule(1L, "Marque1", "Modele1", "SUV", "Rouge", LocalDate.now(), LocalDate.now(), Vehicule.Statut.DISPONIBLE),
                new Vehicule(2L, "Marque2", "Modele2", "SUV", "Noir", LocalDate.now(), LocalDate.now(), Vehicule.Statut.RESERVE));
    }

    @Test
    void createVehicleTest() throws Exception {
        Vehicule newVehicule = new Vehicule(null, "Marque3", "Modele3", "Berline", "Bleu", LocalDate.now(), LocalDate.now(), Vehicule.Statut.DISPONIBLE);
        Vehicule createdVehicule = new Vehicule(3L, "Marque3", "Modele3", "Berline", "Bleu", LocalDate.now(), LocalDate.now(), Vehicule.Statut.DISPONIBLE);

        Mockito.when(vehiculeService.createVehicule(Mockito.any(Vehicule.class))).thenReturn(createdVehicule);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/vehicules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"marque\":\"Marque3\",\"modele\":\"Modele3\",\"typeVehicule\":\"Berline\",\"couleur\":\"Bleu\",\"dateAchat\":\"2024-12-21\",\"dateDerniereMaintenance\":\"2024-12-21\",\"statut\":\"DISPONIBLE\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(createdVehicule.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.marque").value(createdVehicule.getMarque()));

        Mockito.verify(notificationService, Mockito.times(1)).sendUserNotification(Mockito.anyString());
    }

    @Test
    void updateVehicleTest() throws Exception {
        Vehicule existingVehicule = vehiculeList.get(0);
        Vehicule updatedVehicule = new Vehicule(1L, "Marque1", "Modele1", "SUV", "Bleu", LocalDate.now(), LocalDate.now(), Vehicule.Statut.RESERVE);

        Mockito.when(vehiculeService.UpdateVehicule(Mockito.anyLong(), Mockito.any(Vehicule.class))).thenReturn(updatedVehicule);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/vehicules/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"marque\":\"Marque1\",\"modele\":\"Modele1\",\"typeVehicule\":\"SUV\",\"couleur\":\"Bleu\",\"dateAchat\":\"2024-12-21\",\"dateDerniereMaintenance\":\"2024-12-21\",\"statut\":\"RESERVE\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statut").value(updatedVehicule.getStatut().toString()));
    }


    @Test
    void getAllVehiclesTest() throws Exception {
        Mockito.when(vehiculeService.getAllVehicules()).thenReturn(vehiculeList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/vehicules"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(vehiculeList.size()));
    }

    @Test
    void deleteVehicle_Test() throws Exception {
        Mockito.doNothing().when(vehiculeService).deleteVehicule(Mockito.anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/vehicules/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(notificationService, Mockito.times(1)).sendUserNotification(Mockito.anyString());
    }

    @Test
    void getVehicleByIdTest() throws Exception {
        Vehicule vehicule = vehiculeList.get(0);
        Mockito.when(vehiculeService.getVehiculeById(1L)).thenReturn(vehicule);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/vehicules/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(vehicule.getId()));
    }

    @Test
    void getVehiculesDisponiblesTest() throws Exception {
        List<Vehicule> disponibles = List.of(vehiculeList.get(0));
        Mockito.when(vehiculeService.getVehiculesDisponibles()).thenReturn(disponibles);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/vehicules/disponibles"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(disponibles.size()));
    }

    @Test
    void obtenirStatistiquesTest() throws Exception {
        Map<String, Long> stats = Map.of(
                "DISPONIBLE", 5L,
                "RESERVE", 3L
        );

        Mockito.when(vehiculeService.obtenirStatistiques()).thenReturn(stats);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/vehicules/statistiques"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.DISPONIBLE").value(stats.get("DISPONIBLE")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.RESERVE").value(stats.get("RESERVE")));
    }

}
