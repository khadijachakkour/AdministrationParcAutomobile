# Utiliser l'image officielle de PostgreSQL comme base
FROM postgres:latest

# Définir des variables d'environnement pour la base de données
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=postgres
ENV POSTGRES_DB=USERPARC

# Copier des fichiers de configuration supplémentaires si nécessaire (par exemple des scripts d'initialisation)
# COPY init.sql /docker-entrypoint-initdb.d/

# Exposer le port de PostgreSQL
EXPOSE 5432
