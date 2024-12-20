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

// Ajout du Service pour l'injection de dépendances
builder.Services.AddScoped<ReservationService>();

// Ajout des contrôleurs (permet l'affichage des endpoints dans Swagger)
builder.Services.AddControllers();

// Configuration de Swagger pour la documentation des APIs
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var app = builder.Build();

// Tester la connexion à MongoDB
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

/*using Microsoft.Extensions.Options;
using MongoDB.Driver;
using Reservation.Configurations; // Namespace de votre classe MongoDBSettings

var builder = WebApplication.CreateBuilder(args);

// Configurer MongoDB
builder.Services.Configure<MongoDBSettings>(builder.Configuration.GetSection("MongoDB"));
builder.Services.AddSingleton<IMongoClient>(sp =>
{
    var settings = sp.GetRequiredService<IOptions<MongoDBSettings>>().Value;
    return new MongoClient(settings.ConnectionString);
});

builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var app = builder.Build();

// Tester la connexion à MongoDB dans un endpoint simple
app.MapGet("/test-mongodb", (IMongoClient client) =>
{
    try
    {
        // Liste des bases de données disponibles
        var databases = client.ListDatabaseNames().ToList();
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

// Swagger
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();

app.Run();
*/

/*
using Reservation.Configurations; // Assurez-vous que le namespace est correct

var builder = WebApplication.CreateBuilder(args);

// Ajouter la configuration MongoDB à l'injection de dépendances
builder.Services.Configure<MongoDBSettings>(builder.Configuration.GetSection("MongoDB"));

// Ajouter les services nécessaires pour le projet WebAPI
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();

var summaries = new[]
{
    "Freezing", "Bracing", "Chilly", "Cool", "Mild", "Warm", "Balmy", "Hot", "Sweltering", "Scorching"
};

app.MapGet("/weatherforecast", () =>
    {
        var forecast = Enumerable.Range(1, 5).Select(index =>
                new WeatherForecast
                (
                    DateOnly.FromDateTime(DateTime.Now.AddDays(index)),
                    Random.Shared.Next(-20, 55),
                    summaries[Random.Shared.Next(summaries.Length)]
                ))
            .ToArray();
        return forecast;
    })
    .WithName("GetWeatherForecast")
    .WithOpenApi();

app.Run();

record WeatherForecast(DateOnly Date, int TemperatureC, string? Summary)
{
    public int TemperatureF => 32 + (int)(TemperatureC / 0.5556);
}
*/
