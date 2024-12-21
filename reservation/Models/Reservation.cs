using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;

namespace reservation.Models
{
    public class Reservation
    
    {
        //public int Id { get; set; }
         [BsonId]  // Annotations pour MongoDB
        public ObjectId Id { get; set; }  // MongoDB génère automatiquement cet ID
     //   public string Id { get; set; }  // Converti automatiquement ObjectId en string

        public int UserId { get; set; } // Référence à l'utilisateur
        public int VehicleId { get; set; } // Référence au véhicule
        public DateTime ReservationDate { get; set; } // Date de la réservation
        public string Status { get; set; } // Statut de la réservation (confirmée, en attente, etc.)
    }
}