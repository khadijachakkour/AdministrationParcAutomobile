# Étape 1 : Construire avec .NET SDK
FROM mcr.microsoft.com/dotnet/sdk:8.0 AS build-env
WORKDIR /app

# Copier les fichiers
COPY . ./

# Restaurer les dépendances
RUN dotnet restore

# Publier sans AOT
RUN dotnet publish ./reservation -c Release -o out --self-contained=true -r linux-x64

# Étape 2 : Image minimale pour exécuter l'application
FROM mcr.microsoft.com/dotnet/runtime-deps:8.0
WORKDIR /app
COPY --from=build-env /app/out .

# Exposer le port
EXPOSE 5000

# Lancer l'application
ENTRYPOINT ["./reservation"]
