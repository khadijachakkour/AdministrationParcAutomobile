namespace reservation.Interface
{
    // Définition de l'interface IUserService
    public interface IUserService
    {
        Task<int> GetUserIdByEmailAsync(string email);
    }
}