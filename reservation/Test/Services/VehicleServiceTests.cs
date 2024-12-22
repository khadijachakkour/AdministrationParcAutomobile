using System.Net;
using System.Text;
using Moq;
using Moq.Protected;
using NUnit.Framework;
using reservation.Services;

namespace reservation.Test.Services;

[TestFixture]
    public class VehicleServiceTests
    {
        private Mock<HttpMessageHandler> _mockHandler;
        private HttpClient _httpClient;
        private VehicleService _vehicleService;

        [SetUp]
        public void Setup()
        {
            // Création d'un mock de HttpMessageHandler pour simuler la réponse HTTP
            _mockHandler = new Mock<HttpMessageHandler>(MockBehavior.Strict);
            _httpClient = new HttpClient(_mockHandler.Object);
            _vehicleService = new VehicleService(_httpClient);
        }

        [Test]
        public async Task SearchVehicleAsync_ReturnsVehicle_WhenVehicleFound()
        {
            // Arrange
            var marque = "Toyota";
            var modele = "Corolla";
            var typeVehicule = "Citadine";
            var couleur = "Rouge";

            var jsonResponse = "[{\"id\": 1, \"Marque\": \"Toyota\", \"Modele\": \"Corolla\", \"TypeVehicule\": \"Citadine\", \"Couleur\": \"Rouge\", \"Statut\": \"Disponible\", \"DateDerniereMaintenance\": \"2023-12-01T00:00:00\", \"DateAchat\": \"2020-06-15T00:00:00\"}]";
            var httpResponseMessage = new HttpResponseMessage(HttpStatusCode.OK)
            {
                Content = new StringContent(jsonResponse, Encoding.UTF8, "application/json")
            };

            // Utilisation de ReturnsAsync pour retourner une réponse simulée
            _mockHandler
                .Protected()
                .Setup<Task<HttpResponseMessage>>(
                    "SendAsync",
                    ItExpr.Is<HttpRequestMessage>(req => req.Method == HttpMethod.Get && req.RequestUri.ToString().Contains("api/v1/vehicules/filter")),
                    ItExpr.IsAny<CancellationToken>()
                )
                .ReturnsAsync(httpResponseMessage) // Utilisation de ReturnsAsync
                .Verifiable();

            // Act
            var result = await _vehicleService.SearchVehicleAsync(marque, modele, typeVehicule, couleur);

            // Assert
            Assert.IsNotNull(result);
            Assert.AreEqual(1, result.id);
            Assert.AreEqual("Toyota", result.Marque);
            Assert.AreEqual("Corolla", result.Modele);
            Assert.AreEqual("Citadine", result.TypeVehicule);
            Assert.AreEqual("Rouge", result.Couleur);
        }

        [Test]
        public async Task SearchVehicleAsync_ReturnsNull_WhenNoVehicleFound()
        {
            // Arrange
            var marque = "Honda";
            var modele = "Civic";
            var typeVehicule = "SUV";
            var couleur = "Noir";

            var jsonResponse = "[]"; // Aucune donnée
            var httpResponseMessage = new HttpResponseMessage(HttpStatusCode.OK)
            {
                Content = new StringContent(jsonResponse, Encoding.UTF8, "application/json")
            };

            // Utilisation de ReturnsAsync pour retourner une réponse simulée
            _mockHandler
                .Protected()
                .Setup<Task<HttpResponseMessage>>(
                    "SendAsync",
                    ItExpr.Is<HttpRequestMessage>(req => req.Method == HttpMethod.Get && req.RequestUri.ToString().Contains("api/v1/vehicules/filter")),
                    ItExpr.IsAny<CancellationToken>()
                )
                .ReturnsAsync(httpResponseMessage) // Utilisation de ReturnsAsync
                .Verifiable();

            // Act
            var result = await _vehicleService.SearchVehicleAsync(marque, modele, typeVehicule, couleur);

            // Assert
            Assert.IsNull(result);
        }

        [Test]
        public void SearchVehicleAsync_ThrowsException_WhenApiFails()
        {
            // Arrange
            var marque = "Peugeot";
            var modele = "308";
            var typeVehicule = "SUV";
            var couleur = "Bleu";

            var httpResponseMessage = new HttpResponseMessage(HttpStatusCode.BadRequest)
            {
                Content = new StringContent("Erreur de l'API", Encoding.UTF8, "application/json")
            };

            // Utilisation de ReturnsAsync pour retourner une réponse simulée avec erreur
            _mockHandler
                .Protected()
                .Setup<Task<HttpResponseMessage>>(
                    "SendAsync",
                    ItExpr.Is<HttpRequestMessage>(req => req.Method == HttpMethod.Get && req.RequestUri.ToString().Contains("api/v1/vehicules/filter")),
                    ItExpr.IsAny<CancellationToken>()
                )
                .ReturnsAsync(httpResponseMessage)
                .Verifiable();

            // Act & Assert
            var ex = Assert.ThrowsAsync<Exception>(async () => await _vehicleService.SearchVehicleAsync(marque, modele, typeVehicule, couleur));
            Assert.That(ex.Message, Does.Contain("Erreur HTTP"));
        }
    }