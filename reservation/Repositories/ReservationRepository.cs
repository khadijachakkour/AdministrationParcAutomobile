using MongoDB.Driver;
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
            // Insertion de la réservation, MongoDB générera un ObjectId automatiquement
            await _reservations.InsertOneAsync(reservation);
            return reservation;
        }

        // Modification pour accepter un ObjectId
        public async Task<Models.Reservation> GetReservationByIdAsync(ObjectId id)
        {
            return await _reservations.Find(r => r.Id == id).FirstOrDefaultAsync();
        }

        // Modification pour accepter un ObjectId
        public async Task<IEnumerable<Models.Reservation>> GetAllReservationsAsync()
        {
            return await _reservations.Find(r => true).ToListAsync();
        }

        // Modification pour accepter un ObjectId
        public async Task<Models.Reservation> UpdateReservationAsync(Models.Reservation reservation)
        {
            // Mise à jour de la réservation
            var result = await _reservations.ReplaceOneAsync(r => r.Id == reservation.Id, reservation);
            return result.IsAcknowledged ? reservation : null;
        }

        // Modification pour accepter un ObjectId
        public async Task<bool> DeleteReservationAsync(ObjectId id)
        {
            // Suppression de la réservation
            var result = await _reservations.DeleteOneAsync(r => r.Id == id);
            return result.DeletedCount > 0;
        }
    }
}
