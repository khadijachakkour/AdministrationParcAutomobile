using reservation.Models;
using reservation.Repositories;

namespace reservation.Services
{
    public class ReservationService
    {
        private readonly IReservationRepository _reservationRepository;

        public ReservationService(IReservationRepository reservationRepository)
        {
            _reservationRepository = reservationRepository;
        }

        // Créer une réservation
        public async Task<reservation.Models.Reservation> CreateReservationAsync(reservation.Models.Reservation reservation)
        {
            return await _reservationRepository.CreateReservationAsync(reservation);
        }

        // Obtenir une réservation par ID
        public async Task<reservation.Models.Reservation> GetReservationByIdAsync(int id)
        {
            return await _reservationRepository.GetReservationByIdAsync(id);
        }

        // Obtenir toutes les réservations
        public async Task<IEnumerable<reservation.Models.Reservation>> GetAllReservationsAsync()
        {
            return await _reservationRepository.GetAllReservationsAsync();
        }
    }
}