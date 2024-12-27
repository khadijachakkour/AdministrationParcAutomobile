using NUnit.Framework;
using Moq;
using reservation.Controllers;
using reservation.Services;
using reservation.Models;
using Microsoft.AspNetCore.Mvc;
using MongoDB.Bson;
using System.Threading.Tasks;
using reservation.Repositories;
/*
namespace reservation.Tests.Controllers
{
    [TestFixture]
    public class Test_Controlleur
    {
        private Mock<IReservationRepository> _mockReservationRepo;
        private Mock<UserService> _mockUserService;
        private Mock<VehicleService> _mockVehicleService;
        private ReservationController _controller;
        private ReservationService _reservationService;

        [SetUp]
        public void SetUp()
        {
            // Initialisation des mocks pour les dépendances
            _mockReservationRepo = new Mock<IReservationRepository>();
            _mockUserService = new Mock<UserService>();
            _mockVehicleService = new Mock<VehicleService>();

            // Instancier ReservationService avec les mocks
            _reservationService = new ReservationService(
                _mockReservationRepo.Object, 
                _mockUserService.Object, 
                _mockVehicleService.Object
            );

            // Créer le contrôleur avec le service instancié
            _controller = new ReservationController(_reservationService);
        }

        [Test]
        public async Task GetReservationById_ValidId_ReturnsOkResultWithReservation()
        {
            // Arrange
            var id = ObjectId.GenerateNewId().ToString();
            var reservation = new Models.Reservation { Id = new ObjectId(id), Modele = "Test Reservation" };

            _mockReservationRepo.Setup(s => s.GetReservationByIdAsync(It.IsAny<ObjectId>()))
                                .ReturnsAsync(reservation);

            // Act
            var result = await _controller.GetReservationById(id);

            // Assert
            var okResult = result.Result as OkObjectResult;
            Assert.IsNotNull(okResult);
            Assert.AreEqual(200, okResult.StatusCode);
            Assert.AreEqual(reservation, okResult.Value);
        }

        [Test]
        public async Task GetReservationById_InvalidId_ReturnsBadRequest()
        {
            // Arrange
            var invalidId = "invalid-id";

            // Act
            var result = await _controller.GetReservationById(invalidId);

            // Assert
            var badRequestResult = result.Result as BadRequestObjectResult;
            Assert.IsNotNull(badRequestResult);
            Assert.AreEqual(400, badRequestResult.StatusCode);
            Assert.AreEqual("ID invalide.", badRequestResult.Value);
        }

        [Test]
        public async Task GetReservationById_ReservationNotFound_ReturnsNotFound()
        {
            // Arrange
            var id = ObjectId.GenerateNewId().ToString();
            _mockReservationRepo.Setup(s => s.GetReservationByIdAsync(It.IsAny<ObjectId>()))
                                .ReturnsAsync((Models.Reservation)null);

            // Act
            var result = await _controller.GetReservationById(id);

            // Assert
            var notFoundResult = result.Result as NotFoundResult;
            Assert.IsNotNull(notFoundResult);
            Assert.AreEqual(404, notFoundResult.StatusCode);
        }
    }
}
*/