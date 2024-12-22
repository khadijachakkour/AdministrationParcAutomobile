using Microsoft.AspNetCore.Mvc.Testing;
using NUnit.Framework;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;
/*
namespace reservation.Test.Integration
{
    [TestFixture]
    public class ReservationControllerTests
    {
        private HttpClient _client;
        private WebApplicationFactory<Program> _factory; // Remplacer Startup par Program

        [SetUp]
        public void SetUp()
        {
            // Utiliser WebApplicationFactory pour configurer l'application de test
            _factory = new WebApplicationFactory<Program>()
                .WithWebHostBuilder(builder =>
                {
                    // Vous pouvez ajouter des services ou des configurations supplémentaires ici si nécessaire
                });

            _client = _factory.CreateClient(); // Utilisation du client HTTP pour envoyer des requêtes
        }

        [Test]
        public async Task CreateReservation_ShouldReturnCreatedReservation()
        {
            var reservation = new
            {
                Marque = "Toyota",
                Modele = "Corolla",
                Couleur = "Blue"
            };

            var content = new StringContent(JsonConvert.SerializeObject(reservation), Encoding.UTF8, "application/json");

            var response = await _client.PostAsync("/api/reservation", content);

            Assert.AreEqual(System.Net.HttpStatusCode.Created, response.StatusCode);

            var responseContent = await response.Content.ReadAsStringAsync();
            var createdReservation = JsonConvert.DeserializeObject<dynamic>(responseContent);

            Assert.AreEqual("Toyota", createdReservation.Marque.ToString());
            Assert.AreEqual("Corolla", createdReservation.Modele.ToString());
            Assert.AreEqual("Blue", createdReservation.Couleur.ToString());
        }

        [Test]
        public async Task GetReservation_ShouldReturnReservation()
        {
            var reservation = new
            {
                Marque = "Honda",
                Modele = "Civic",
                Couleur = "Red"
            };

            var content = new StringContent(JsonConvert.SerializeObject(reservation), Encoding.UTF8, "application/json");
            var postResponse = await _client.PostAsync("/api/reservation", content);
            var postResponseContent = await postResponse.Content.ReadAsStringAsync();
            var createdReservation = JsonConvert.DeserializeObject<dynamic>(postResponseContent);

            var getResponse = await _client.GetAsync($"/api/reservation/{createdReservation.id}");

            Assert.AreEqual(System.Net.HttpStatusCode.OK, getResponse.StatusCode);

            var getResponseContent = await getResponse.Content.ReadAsStringAsync();
            var fetchedReservation = JsonConvert.DeserializeObject<dynamic>(getResponseContent);

            Assert.AreEqual("Honda", fetchedReservation.Marque.ToString());
            Assert.AreEqual("Civic", fetchedReservation.Modele.ToString());
            Assert.AreEqual("Red", fetchedReservation.Couleur.ToString());
        }
    }
}
*/
