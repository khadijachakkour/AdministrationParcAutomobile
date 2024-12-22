using System.Text.Json;
using reservation.Interface;

namespace reservation.Services
{
    // Implémentation de l'interface IUserService par la classe UserService
    public class UserService : IUserService
    {
        private readonly HttpClient _httpClient;
        
        /*
         * ajouter un constructeur userservice sans parametre
         */

        // Constructeur sans paramètre pour initialiser l'HttpClient
        public UserService()
        {
            _httpClient = new HttpClient
            {
                BaseAddress = new Uri("http://localhost:8090") // Assurez-vous que l'URL correspond à votre API
            };
        }

        // Constructeur pour injecter un HttpClient déjà configuré
        public UserService(HttpClient httpClient)
        {
            _httpClient = httpClient;
            _httpClient.BaseAddress = new Uri("http://localhost:8090"); // URL de base de l'API utilisateur
        }

        // Méthode pour récupérer l'ID utilisateur par email
        public virtual async Task<int> GetUserIdByEmailAsync(string email)
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

    // Classe DTO pour représenter la réponse de l'API utilisateur.
    public class UserResponse
    {
        public string id { get; set; }
        public string Email { get; set; }
    }
}
