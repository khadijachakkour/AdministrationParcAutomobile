namespace reservation.Models
{
    public class Reservation
    {
        public int Id { get; set; }
        public int UserId { get; set; } // Référence à l'utilisateur
        public int VehicleId { get; set; } // Référence au véhicule
        public DateTime ReservationDate { get; set; } // Date de la réservation
        public string Status { get; set; } // Statut de la réservation (confirmée, en attente, etc.)
    }
}