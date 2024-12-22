using System.Net;
using System.Text;
using Moq;
using Moq.Protected;
using NUnit.Framework;
using reservation.Services;


namespace reservation.Test.Services;

 [TestFixture]
    public class UserServiceTests
    {
        private Mock<HttpMessageHandler> _mockHandler;
        private HttpClient _httpClient;
        private UserService _userService;

        [SetUp]
        public void Setup()
        {
            // Création d'un mock de HttpMessageHandler pour simuler la réponse HTTP
            _mockHandler = new Mock<HttpMessageHandler>(MockBehavior.Strict);
            _httpClient = new HttpClient(_mockHandler.Object);
            _userService = new UserService(_httpClient);
        }

        [Test]
        public async Task GetUserIdByEmailAsync_ReturnsValidUserId_WhenEmailExists()
        {
            // Arrange
            var email = "test@example.com";
            var jsonResponse = "{\"id\":\"123\", \"Email\":\"test@example.com\"}";
            var httpResponseMessage = new HttpResponseMessage(HttpStatusCode.OK)
            {
                Content = new StringContent(jsonResponse, Encoding.UTF8, "application/json")
            };

            // Utilisation de ReturnsAsync pour retourner une réponse simulée
            _mockHandler
                .Protected()
                .Setup<Task<HttpResponseMessage>>(
                    "SendAsync",
                    ItExpr.Is<HttpRequestMessage>(req => req.Method == HttpMethod.Get && req.RequestUri.ToString().Contains($"api/users/email/{email}")),
                    ItExpr.IsAny<CancellationToken>()
                )
                .ReturnsAsync(httpResponseMessage) // Utilisation correcte de ReturnsAsync
                .Verifiable();

            // Act
            var result = await _userService.GetUserIdByEmailAsync(email);

            // Assert
            Assert.AreEqual(123, result);
        }

        [Test]
        public void GetUserIdByEmailAsync_ThrowsException_WhenEmailNotFound()
        {
            // Arrange
            var email = "nonexistent@example.com";
            var httpResponseMessage = new HttpResponseMessage(HttpStatusCode.NotFound);

            // Utilisation de ReturnsAsync pour retourner une réponse simulée
            _mockHandler
                .Protected()
                .Setup<Task<HttpResponseMessage>>(
                    "SendAsync",
                    ItExpr.Is<HttpRequestMessage>(req => req.Method == HttpMethod.Get && req.RequestUri.ToString().Contains($"api/users/email/{email}")),
                    ItExpr.IsAny<CancellationToken>()
                )
                .ReturnsAsync(httpResponseMessage) // Utilisation correcte de ReturnsAsync
                .Verifiable();

            // Act & Assert
            var ex = Assert.ThrowsAsync<Exception>(async () => await _userService.GetUserIdByEmailAsync(email));
            Assert.That(ex.Message, Does.Contain("Erreur HTTP"));
        }

        [Test]
        public void GetUserIdByEmailAsync_ThrowsException_WhenInvalidUserId()
        {
            // Arrange
            var email = "invalidid@example.com";
            var jsonResponse = "{\"id\":\"abc\", \"Email\":\"invalidid@example.com\"}";
            var httpResponseMessage = new HttpResponseMessage(HttpStatusCode.OK)
            {
                Content = new StringContent(jsonResponse, Encoding.UTF8, "application/json")
            };

            // Utilisation de ReturnsAsync pour retourner une réponse simulée
            _mockHandler
                .Protected()
                .Setup<Task<HttpResponseMessage>>(
                    "SendAsync",
                    ItExpr.Is<HttpRequestMessage>(req => req.Method == HttpMethod.Get && req.RequestUri.ToString().Contains($"api/users/email/{email}")),
                    ItExpr.IsAny<CancellationToken>()
                )
                .ReturnsAsync(httpResponseMessage) // Utilisation correcte de ReturnsAsync
                .Verifiable();

            // Act & Assert
            var ex = Assert.ThrowsAsync<Exception>(async () => await _userService.GetUserIdByEmailAsync(email));
            Assert.That(ex.Message, Does.Contain("ID utilisateur invalide"));
        }
        }