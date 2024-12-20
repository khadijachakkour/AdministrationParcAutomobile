using reservation.Models;

namespace reservation.Repositories
{
    public interface IReservationRepository
    {
        Task<Models.Reservation> CreateReservationAsync(Models.Reservation reservation);
        Task<Models.Reservation> GetReservationByIdAsync(int id);
        Task<IEnumerable<Models.Reservation>> GetAllReservationsAsync();
    }
}