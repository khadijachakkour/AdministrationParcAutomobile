using Microsoft.AspNetCore.Mvc;
using reservation.Services;
using MongoDB.Bson; // Ajouter la dépendance MongoDB pour ObjectId

namespace reservation.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class ReservationController : ControllerBase
    {
        private readonly ReservationService _reservationService;

        public ReservationController(ReservationService reservationService)
        {
            _reservationService = reservationService;
        }

        [HttpPost]
        public async Task<ActionResult<Models.Reservation>> CreateReservation(Models.Reservation reservation)
        {
            var createdReservation = await _reservationService.CreateReservationAsync(reservation);
            return CreatedAtAction(nameof(GetReservationById), new { id = createdReservation.Id }, createdReservation);
        }

        [HttpGet("{id}")]
        public async Task<ActionResult<Models.Reservation>> GetReservationById(string id)
        {
            if (!ObjectId.TryParse(id, out var objectId)) // Convertir string à ObjectId
            {
                return BadRequest("ID invalide.");
            }

            var reservation = await _reservationService.GetReservationByIdAsync(objectId);
            if (reservation == null)
            {
                return NotFound();
            }

            return Ok(reservation);
        }
        
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Models.Reservation>>> GetAllReservations()
        {
            var reservations = await _reservationService.GetAllReservationsAsync();
            return Ok(reservations);
        }

        [HttpPut("{id}")]
        public async Task<ActionResult<Models.Reservation>> UpdateReservation(string id, Models.Reservation reservation)
        {
            if (!ObjectId.TryParse(id, out var objectId)) // Convertir string à ObjectId
            {
                return BadRequest("ID invalide.");
            }

            var updatedReservation = await _reservationService.UpdateReservationAsync(objectId, reservation);
            if (updatedReservation == null)
            {
                return NotFound();
            }

            return Ok(updatedReservation);
        }

        [HttpDelete("{id}")]
        public async Task<ActionResult> DeleteReservation(string id, [FromQuery] DateTime cancellationTime)
        {
            if (!ObjectId.TryParse(id, out var objectId)) // Convertir string à ObjectId
            {
                return BadRequest("ID invalide.");
            }

            var result = await _reservationService.DeleteReservationAsync(objectId, cancellationTime);

            if (!result)
            {
                return BadRequest("Impossible d'annuler cette réservation.");
            }

            return NoContent();
        }
    }
}
