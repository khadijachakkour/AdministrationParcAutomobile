using reservation.Repositories;
using MongoDB.Bson; // Importer MongoDB.Bson pour ObjectId
using reservation.Models;
using System;

namespace reservation.Services
{
    public class ReservationService
    {
        private readonly IReservationRepository _reservationRepository;

        public ReservationService(IReservationRepository reservationRepository)
        {
            _reservationRepository = reservationRepository;
        }

        public async Task<Models.Reservation> CreateReservationAsync(Models.Reservation reservation)
        {
            reservation.Status = "En attente"; // Le statut par défaut
            return await _reservationRepository.CreateReservationAsync(reservation);
        }

        public async Task<Models.Reservation> GetReservationByIdAsync(ObjectId id) // Accepter ObjectId
        {
            return await _reservationRepository.GetReservationByIdAsync(id);
        }

        public async Task<IEnumerable<Models.Reservation>> GetAllReservationsAsync()
        {
            return await _reservationRepository.GetAllReservationsAsync();
        }

        public async Task<Models.Reservation> UpdateReservationAsync(ObjectId id, Models.Reservation updatedReservation) // Accepter ObjectId
        {
            var existingReservation = await _reservationRepository.GetReservationByIdAsync(id);
            if (existingReservation == null)
            {
                throw new Exception("Réservation non trouvée");
            }

            existingReservation.UserId = updatedReservation.UserId;
            existingReservation.VehicleId = updatedReservation.VehicleId;
            existingReservation.ReservationDate = updatedReservation.ReservationDate;
            existingReservation.Status = "Confirmée"; // Exemple de changement de statut

            return await _reservationRepository.UpdateReservationAsync(existingReservation);
        }

        public async Task<bool> DeleteReservationAsync(ObjectId id, DateTime cancellationTime) // Accepter ObjectId
        {
            var reservation = await _reservationRepository.GetReservationByIdAsync(id);

            if (reservation == null)
            {
                throw new Exception("Réservation non trouvée");
            }

            if (cancellationTime <= DateTime.Now)
            {
                reservation.Status = "Annulée"; // Mettre à jour le statut à "Annulée"
                await _reservationRepository.UpdateReservationAsync(reservation); // Sauvegarder le statut annulé
                return true;
            }

            return false;
        }
    }
}
