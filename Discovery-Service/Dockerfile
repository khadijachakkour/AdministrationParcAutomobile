# Utiliser l'image officielle de Java
FROM openjdk:17-jdk-slim

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier .jar généré dans le répertoire de travail du conteneur
COPY target/Discovery-Service-0.0.1-SNAPSHOT.jar app.jar

# Exposer le port sur lequel Spring Boot s'exécute
EXPOSE 8761

# Démarrer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
