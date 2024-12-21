using reservation.Repositories;
using MongoDB.Bson; // Importer MongoDB.Bson pour ObjectId
using reservation.Models;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace reservation.Services
{
    public class ReservationService
    {
        private readonly IReservationRepository _reservationRepository;
        private readonly UserService _userService; // Ajouter une dépendance vers UserService

        // Ajouter UserService dans le constructeur
        public ReservationService(IReservationRepository reservationRepository, UserService userService)
        {
            _reservationRepository = reservationRepository;
            _userService = userService;
        }

        // Méthode pour créer une réservation avec l'ID utilisateur obtenu par l'email
        public async Task<Models.Reservation> CreateReservationAsync(Models.Reservation reservation)
        {
            // Récupérer l'ID utilisateur par email (assurez-vous que GetUserIdByEmailAsync renvoie un int)
            var userId = await _userService.GetUserIdByEmailAsync(reservation.Email);
            reservation.UserId = userId; // Assigner l'ID utilisateur à la réservation

            reservation.Status = "En attente"; // Le statut par défaut
            return await _reservationRepository.CreateReservationAsync(reservation);
        }

        // Méthode pour obtenir une réservation par ID
        public async Task<Models.Reservation> GetReservationByIdAsync(ObjectId id)
        {
            return await _reservationRepository.GetReservationByIdAsync(id);
        }

        // Méthode pour obtenir toutes les réservations
        public async Task<IEnumerable<Models.Reservation>> GetAllReservationsAsync()
        {
            return await _reservationRepository.GetAllReservationsAsync();
        }

        // Méthode pour mettre à jour une réservation
        public async Task<Models.Reservation> UpdateReservationAsync(ObjectId id, Models.Reservation updatedReservation)
        {
            var existingReservation = await _reservationRepository.GetReservationByIdAsync(id);
            if (existingReservation == null)
            {
                throw new Exception("Réservation non trouvée");
            }

            // Récupérer l'ID utilisateur par email lors de la mise à jour (assurez-vous que GetUserIdByEmailAsync renvoie un int)
            var userId = await _userService.GetUserIdByEmailAsync(updatedReservation.Email);
            existingReservation.UserId = userId;

            existingReservation.VehicleId = updatedReservation.VehicleId;
            existingReservation.ReservationDate = updatedReservation.ReservationDate;
            existingReservation.Status = "Confirmée"; // Exemple de changement de statut

            return await _reservationRepository.UpdateReservationAsync(existingReservation);
        }

        // Méthode pour supprimer une réservation
        public async Task<bool> DeleteReservationAsync(ObjectId id, DateTime cancellationTime)
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
