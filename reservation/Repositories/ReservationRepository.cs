using MongoDB.Driver;
using reservation.Models;

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
            await _reservations.InsertOneAsync(reservation);
            return reservation;
        }

        public async Task<Models.Reservation> GetReservationByIdAsync(int id)
        {
            return await _reservations.Find(r => r.Id == id).FirstOrDefaultAsync();
        }

        public async Task<IEnumerable<Models.Reservation>> GetAllReservationsAsync()
        {
            return await _reservations.Find(r => true).ToListAsync();
        }
    }
}