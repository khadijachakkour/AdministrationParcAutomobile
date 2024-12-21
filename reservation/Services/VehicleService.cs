using System;
using System.Net.Http;
using System.Text.Json;
using System.Threading.Tasks;
using System.Collections.Generic;

namespace reservation.Services
{
    /// <summary>
    /// Service pour interagir avec l'API des véhicules.
    /// </summary>
    public class VehicleService
    {
        private readonly HttpClient _httpClient;

        /// <summary>
        /// Constructeur pour injecter le HttpClient.
        /// </summary>
        /// <param name="httpClient">Client HTTP configuré pour effectuer les appels à l'API des véhicules.</param>
        public VehicleService(HttpClient httpClient)
        {
            _httpClient = httpClient;
            _httpClient.BaseAddress = new Uri("http://localhost:8081"); // URL de base de l'API des véhicules
        }

        /// <summary>
        /// Recherche un véhicule par ses critères (marque, modèle, type, couleur).
        /// </summary>
        /// <param name="marque">Marque du véhicule.</param>
        /// <param name="modele">Modèle du véhicule.</param>
        /// <param name="typeVehicule">Type du véhicule (ex : citadine, SUV, etc.).</param>
        /// <param name="couleur">Couleur du véhicule.</param>
        /// <returns>Le véhicule trouvé ou null si aucun véhicule ne correspond.</returns>
        public async Task<VehicleResponse> SearchVehicleAsync(string marque, string modele, string typeVehicule, string couleur)
        {
            try
            {
                var response = await _httpClient.GetAsync($"/api/v1/vehicules/filter?marque={marque}&modele={modele}&typeVehicule={typeVehicule}&couleur={couleur}");

                if (!response.IsSuccessStatusCode)
                {
                    throw new HttpRequestException($"Erreur HTTP : {response.StatusCode}, lors de la recherche du véhicule.");
                }

                var jsonResponse = await response.Content.ReadAsStringAsync();
                Console.WriteLine($"Réponse JSON : {jsonResponse}"); // Log pour la réponse JSON

                var vehicles = JsonSerializer.Deserialize<List<VehicleResponse>>(jsonResponse);

                if (vehicles == null || vehicles.Count == 0)
                {
                    return null; // Aucun véhicule trouvé
                }

                return vehicles[0]; // Retourner le premier véhicule trouvé
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Erreur dans SearchVehicleAsync pour {marque} {modele}: {ex.Message}");
                throw new Exception($"Erreur lors de la recherche du véhicule {marque} {modele}: {ex.Message}", ex);
            }
        }
    }

    /// <summary>
    /// Classe DTO pour représenter la réponse de l'API des véhicules.
    /// </summary>
    public class VehicleResponse
    {
        public int id { get; set; }
        public string Marque { get; set; }
        public string Modele { get; set; }
        public string TypeVehicule { get; set; }
        public string Couleur { get; set; }
        public string Statut { get; set; }
        public DateTime DateDerniereMaintenance { get; set; }
        public DateTime DateAchat { get; set; }
    }
}
