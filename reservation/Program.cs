using Microsoft.AspNetCore.Builder;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using MongoDB.Driver;
using reservation.Models;
using reservation.Repositories;
using reservation.Services;
using Microsoft.Extensions.Options;
using Reservation.Configurations;

var builder = WebApplication.CreateBuilder(args);

// Configuration de MongoDB dans l'injection de dépendances
builder.Services.Configure<MongoDBSettings>(builder.Configuration.GetSection("MongoDB"));
builder.Services.AddSingleton<IMongoClient>(sp =>
{
    var settings = sp.GetRequiredService<IOptions<MongoDBSettings>>().Value;
    return new MongoClient(settings.ConnectionString);
});

// Ajout du Repository pour l'injection de dépendances
builder.Services.AddScoped<IReservationRepository, ReservationRepository>();

// Ajout du Service de réservation pour l'injection de dépendances
builder.Services.AddScoped<ReservationService>();

// Configuration de HttpClient pour le UserService
builder.Services.AddHttpClient<UserService>(client =>
{
    client.BaseAddress = new Uri("http://localhost:8090"); // URL de base pour le service utilisateur
});

// Configuration de HttpClient pour le VehicleService
builder.Services.AddHttpClient<VehicleService>(client =>
{
    client.BaseAddress = new Uri("http://localhost:8081"); // URL de base pour le service des véhicules
});

// Ajout de CORS
builder.Services.AddCors(options =>
{
    options.AddPolicy("AllowAngularApp", policy =>
    {
        policy.WithOrigins("http://localhost:4200") // Adresse du frontend Angular mise à jour
            .AllowAnyHeader()
            .AllowAnyMethod();
    });
});


// Ajout des contrôleurs (permet l'affichage des endpoints dans Swagger)
builder.Services.AddControllers();

// Configuration de Swagger pour la documentation des APIs
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var app = builder.Build();

// Endpoint pour tester la connexion à MongoDB
app.MapGet("/test-mongodb", async (IMongoClient client) =>
{
    try
    {
        var databases = await client.ListDatabaseNames().ToListAsync();
        return Results.Ok(new
        {
            Message = "Connexion réussie à MongoDB.",
            Databases = databases
        });
    }
    catch (Exception ex)
    {
        return Results.Problem($"Erreur lors de la connexion à MongoDB : {ex.Message}");
    }
});

// Configuration de Swagger dans l'environnement de développement
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI(c =>
    {
        c.RoutePrefix = "swagger";  // Swagger sera accessible via http://localhost:5106/swagger
    });
}

// Désactivation de la redirection HTTPS pour le développement local (si nécessaire)
app.UseHttpsRedirection();  // À commenter si vous ne souhaitez pas forcer HTTPS en développement

// Appliquer la politique CORS
app.UseCors("AllowAngularApp");

// Configuration des contrôleurs
app.MapControllers();

// Exécuter l'application
app.Run();

/*
using MongoDB.Driver;
using reservation.Models;
using reservation.Repositories;
using reservation.Services;
using Microsoft.Extensions.Options;
using Reservation.Configurations;

var builder = WebApplication.CreateBuilder(args);

// Configuration de MongoDB dans l'injection de dépendances
builder.Services.Configure<MongoDBSettings>(builder.Configuration.GetSection("MongoDB"));
builder.Services.AddSingleton<IMongoClient>(sp =>
{
    var settings = sp.GetRequiredService<IOptions<MongoDBSettings>>().Value;
    return new MongoClient(settings.ConnectionString);
});

// Ajout du Repository pour l'injection de dépendances
builder.Services.AddScoped<IReservationRepository, ReservationRepository>();

// Ajout du Service de réservation pour l'injection de dépendances
builder.Services.AddScoped<ReservationService>();

// Configuration de HttpClient pour le UserService
builder.Services.AddHttpClient<UserService>(client =>
{
    client.BaseAddress = new Uri("http://localhost:8090"); // URL de base pour le service utilisateur
});

// Configuration de HttpClient pour le VehicleService
builder.Services.AddHttpClient<VehicleService>(client =>
{
    client.BaseAddress = new Uri("http://localhost:8081"); // URL de base pour le service des véhicules (remplacez par l'URL appropriée)
});

// Ajout des contrôleurs (permet l'affichage des endpoints dans Swagger)
builder.Services.AddControllers();

// Configuration de Swagger pour la documentation des APIs
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var app = builder.Build();

// Endpoint pour tester la connexion à MongoDB
app.MapGet("/test-mongodb", async (IMongoClient client) =>
{
    try
    {
        var databases = await client.ListDatabaseNames().ToListAsync();
        return Results.Ok(new
        {
            Message = "Connexion réussie à MongoDB.",
            Databases = databases
        });
    }
    catch (Exception ex)
    {
        return Results.Problem($"Erreur lors de la connexion à MongoDB : {ex.Message}");
    }
});

// Configuration de Swagger dans l'environnement de développement
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI(c =>
    {
        c.RoutePrefix = "swagger";  // Swagger sera accessible via http://localhost:5106/swagger
    });
}

// Désactivation de la redirection HTTPS pour le développement local (si nécessaire)
app.UseHttpsRedirection();  // À commenter si vous ne souhaitez pas forcer HTTPS en développement

// Configuration des contrôleurs
app.MapControllers();

// Exécuter l'application
app.Run();
*/