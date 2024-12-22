using System.Threading.Tasks;
using reservation.Services;

namespace reservation.Interface
{
    public interface IVehicleService
    {
        Task<VehicleResponse> SearchVehicleAsync(string marque, string modele, string typeVehicule, string couleur);
    }
}