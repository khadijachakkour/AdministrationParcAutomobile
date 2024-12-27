using MongoDB.Bson;
using Moq;
using NUnit.Framework;
using reservation.Repositories;
using reservation.Services;
/*
namespace reservation.Test.Services;

[TestFixture]
    public class ReservationServiceTests
    {
        private Mock<IReservationRepository> _reservationRepositoryMock;
        private Mock<UserService> _userServiceMock;
        private Mock<VehicleService> _vehicleServiceMock;
        private ReservationService _reservationService;

        [SetUp]
        public void SetUp()
        {
            _reservationRepositoryMock = new Mock<IReservationRepository>();
            _userServiceMock = new Mock<UserService>();
            _vehicleServiceMock = new Mock<VehicleService>();
            _reservationService = new ReservationService(_reservationRepositoryMock.Object, _userServiceMock.Object, _vehicleServiceMock.Object);
        }

       
        

        [Test]
        public async Task UpdateReservationAsync_ShouldReturnUpdatedReservation_WhenValidInput()
        {
            // Arrange
            var reservationId = ObjectId.GenerateNewId();
            var existingReservation = new Models.Reservation
            {
                Id = reservationId,
                Email = "user@example.com",
                Marque = "Honda",
                Modele = "Civic",
                TypeVehicule = "Sedan",
                Couleur = "Black",
                Status = "En attente"
            };

            var updatedReservation = new Models.Reservation
            {
                Email = "user@example.com",
                Marque = "Honda",
                Modele = "Civic",
                TypeVehicule = "Sedan",
                Couleur = "Black",
                Status = "Confirmée"
            };

            _reservationRepositoryMock.Setup(repo => repo.GetReservationByIdAsync(reservationId))
                                      .ReturnsAsync(existingReservation);
            _userServiceMock.Setup(service => service.GetUserIdByEmailAsync(updatedReservation.Email))
                            .ReturnsAsync(1);

            _reservationRepositoryMock.Setup(repo => repo.UpdateReservationAsync(It.IsAny<Models.Reservation>()))
                                      .ReturnsAsync((Models.Reservation r) =>
                                      {
                                          r.Status = "Confirmée";
                                          return r;
                                      });

            // Act
            var result = await _reservationService.UpdateReservationAsync(reservationId, updatedReservation);

            // Assert
            Assert.NotNull(result);
            Assert.AreEqual("Confirmée", result.Status);
        }
    }
    */