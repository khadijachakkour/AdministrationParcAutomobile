﻿using MongoDB.Driver;
using MongoDB.Bson;
using reservation.Models;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace reservation.Repositories
{
    public class ReservationRepository : IReservationRepository
    {
        private readonly IMongoCollection<Models.Reservation> _reservations;

        public ReservationRepository(IMongoClient mongoClient)
        {
            var database = mongoClient.GetDatabase("ReservationDB");
            _reservations = database.GetCollection<Models.Reservation>("Reservations");
        }

        public async Task<Models.Reservation> CreateReservationAsync(Models.Reservation reservation)
        {
            // Insertion de la réservation dans la base de données MongoDB
            await _reservations.InsertOneAsync(reservation);
            return reservation;
        }

        // Méthode pour obtenir une réservation par son ObjectId
        public async Task<Models.Reservation> GetReservationByIdAsync(ObjectId id)
        {
            // Recherche de la réservation par son ObjectId
            return await _reservations.Find(r => r.Id == id).FirstOrDefaultAsync();
        }

        // Méthode pour obtenir toutes les réservations
        public async Task<IEnumerable<Models.Reservation>> GetAllReservationsAsync()
        {
            // Retourner toutes les réservations
            return await _reservations.Find(r => true).ToListAsync();
        }

        // Méthode pour mettre à jour une réservation
        public async Task<Models.Reservation> UpdateReservationAsync(Models.Reservation reservation)
        {
            // Mise à jour de la réservation dans la base de données
            var result = await _reservations.ReplaceOneAsync(r => r.Id == reservation.Id, reservation);
            return result.IsAcknowledged ? reservation : null;
        }

        // Méthode pour supprimer une réservation
        public async Task<bool> DeleteReservationAsync(ObjectId id)
        {
            // Suppression de la réservation par son ObjectId
            var result = await _reservations.DeleteOneAsync(r => r.Id == id);
            return result.DeletedCount > 0;
        }
    }
}
