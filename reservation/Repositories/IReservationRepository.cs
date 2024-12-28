using MongoDB.Bson;
using reservation.Models;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace reservation.Repositories
{
    public interface IReservationRepository
    {
        Task<Models.Reservation> CreateReservationAsync(Models.Reservation reservation);
        
        // Utilisation d'ObjectId pour correspondre au type MongoDB
        Task<Models.Reservation> GetReservationByIdAsync(ObjectId id);
        
        Task<IEnumerable<Models.Reservation>> GetAllReservationsAsync();
        
        Task<Models.Reservation> UpdateReservationAsync(Models.Reservation reservation);
        
        // Utilisation d'ObjectId pour correspondre au type MongoDB
        Task<bool> DeleteReservationAsync(ObjectId id);
    }
}