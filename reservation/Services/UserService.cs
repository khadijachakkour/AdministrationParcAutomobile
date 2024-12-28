using System;
using System.Net.Http;
using System.Text.Json;
using System.Threading.Tasks;

namespace reservation.Services
{
    /// <summary>
    /// Service pour interagir avec l'API utilisateur.
    /// </summary>
    public class UserService
    {
        private readonly HttpClient _httpClient;

        /// <summary>
        /// Constructeur pour injecter le HttpClient.
        /// </summary>
        /// <param name="httpClient">Client HTTP configuré pour effectuer les appels à l'API utilisateur.</param>
        public UserService(HttpClient httpClient)
        {
            _httpClient = httpClient;
            _httpClient.BaseAddress = new Uri("http://localhost:8090"); // URL de base de l'API utilisateur
        }

        /// <summary>
        /// Récupère l'ID utilisateur à partir de l'adresse e-mail.
        /// </summary>
        /// <param name="email">Adresse e-mail de l'utilisateur.</param>
        /// <returns>ID utilisateur sous forme d'entier.</returns>
        public async Task<int> GetUserIdByEmailAsync(string email)
        {
            try
            {
                var response = await _httpClient.GetAsync($"/api/users/email/{email}");
        
                if (!response.IsSuccessStatusCode)
                {
                    throw new HttpRequestException($"Erreur HTTP : {response.StatusCode}, lors de la récupération de l'utilisateur.");
                }

                var jsonResponse = await response.Content.ReadAsStringAsync();
                Console.WriteLine($"Réponse JSON : {jsonResponse}"); // Log pour la réponse JSON

                var user = JsonSerializer.Deserialize<UserResponse>(jsonResponse);

                // Vérifier que l'utilisateur existe et que l'ID est valide
                if (user == null)
                {
                    throw new Exception($"Aucun utilisateur trouvé pour l'email {email}: réponse vide");
                }

                Console.WriteLine($"Utilisateur trouvé: ID = {user.id}, Email = {user.Email}");

                if (string.IsNullOrEmpty(user.id))
                {
                    throw new Exception($"ID utilisateur introuvable pour l'email {email}");
                }

                // Vérification et conversion de l'ID utilisateur
                if (!int.TryParse(user.id, out int userId))
                {
                    throw new Exception($"ID utilisateur invalide pour l'utilisateur {email}: {user.id}");
                }

                Console.WriteLine($"ID utilisateur valide : {userId}");
                return userId;
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Erreur dans GetUserIdByEmailAsync pour l'email {email}: {ex.Message}");
                throw new Exception($"Erreur lors de la récupération de l'utilisateur avec l'email {email}: {ex.Message}", ex);
            }
        }
    }

    /// <summary>
    /// Classe DTO pour représenter la réponse de l'API utilisateur.
    /// </summary>
    public class UserResponse
    {
        public string id { get; set; }
        public string Email { get; set; }
       // public string Password { get; set; }  // Si vous avez besoin du mot de passe
        //public string Scope { get; set; }    // Si vous avez besoin du scope
    }
}
