# Utiliser une image Python officielle
FROM python:3.13.1

# Définir le répertoire de travail
WORKDIR /app

# Copier les fichiers nécessaires
COPY . /app

# Installer les dépendances Python
RUN pip install --no-cache-dir -r requirements.txt

# Exposer le port 5010
EXPOSE 5010
# Lancer l'application Flask avec eventlet pour SocketIO
CMD ["python", "app.py"]
