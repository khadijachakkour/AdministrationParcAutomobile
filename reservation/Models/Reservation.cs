using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;

namespace reservation.Models
{
    public class Reservation
    {
        [BsonId]  // Annotations pour MongoDB
        public ObjectId Id { get; set; }  // MongoDB génère automatiquement cet ID
        
        public int UserId { get; set; } // Référence à l'utilisateur
        public int VehicleId { get; set; } // Référence au véhicule
        public DateTime ReservationDate { get; set; } // Date de la réservation
        public string Status { get; set; } // Statut de la réservation (confirmée, en attente, etc.)
        
        // Optionnellement, si vous voulez garder un champ Email directement dans la réservation
        public string Email { get; set; }  // Email de l'utilisateur associé à la réservation
        
        public string Marque { get; set; }
        public string Modele { get; set; }
        public string TypeVehicule { get; set; }
        public string Couleur { get; set; }
    }
}