using reservation.Repositories;
using MongoDB.Bson; // Importer MongoDB.Bson pour ObjectId
using reservation.Models;
using System;
using System.Collections.Generic;
using System.Text.Json;
using System.Threading.Tasks;

namespace reservation.Services
{
    public class ReservationService
    {
        private readonly IReservationRepository _reservationRepository;
        private readonly UserService _userService; // Ajouter une dépendance vers UserService
        private readonly VehicleService _vehicleService; // Dépendance vers VehicleService
        
        // Constructeur sans paramètre (nécessaire pour Moq)
        public ReservationService() { }

        // Ajouter UserService dans le constructeur
        public ReservationService(IReservationRepository reservationRepository, UserService userService , VehicleService vehicleService)
        {
            _reservationRepository = reservationRepository;
            _userService = userService;
            _vehicleService = vehicleService; // Injection du VehicleService
        }

        // Méthode pour créer une réservation avec l'ID utilisateur obtenu par l'email
        /*
        public async Task<Models.Reservation> CreateReservationAsync(Models.Reservation reservation)
        {
            // Récupérer l'ID utilisateur par email (assurez-vous que GetUserIdByEmailAsync renvoie un int)
            var userId = await _userService.GetUserIdByEmailAsync(reservation.Email);
            reservation.UserId = userId; // Assigner l'ID utilisateur à la réservation
            
            // Recherche du véhicule via l'API des véhicules
            var vehicle = await _vehicleService.SearchVehicleAsync(reservation.Marque, reservation.Modele, reservation.TypeVehicule, reservation.Couleur);

            if (vehicle == null)
            {
                throw new Exception("Aucun véhicule correspondant trouvé.");
            }
            // Assigner le véhicule à la réservation
            reservation.VehicleId = vehicle.id;
            reservation.Status = "En attente"; // Le statut par défaut
            // Créer la réservation
            return await _reservationRepository.CreateReservationAsync(reservation);
        }
        */
        public async Task<Models.Reservation> CreateReservationAsync(Models.Reservation reservation)
        {
            Console.WriteLine("Création d'une réservation : " + JsonSerializer.Serialize(reservation)); // Log des détails de la réservation

            // Récupérer l'ID utilisateur par email
            var userId = await _userService.GetUserIdByEmailAsync(reservation.Email);
            reservation.UserId = userId; // Assigner l'ID utilisateur à la réservation

            // Recherche du véhicule via l'API des véhicules
            var vehicle = await _vehicleService.SearchVehicleAsync(reservation.Marque, reservation.Modele, reservation.TypeVehicule, reservation.Couleur);

            if (vehicle == null)
            {
                throw new Exception("Aucun véhicule correspondant trouvé.");
            }

            // Assigner le véhicule à la réservation
            reservation.VehicleId = vehicle.id;
            reservation.Status = "En attente"; // Le statut par défaut

            // Créer la réservation
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
