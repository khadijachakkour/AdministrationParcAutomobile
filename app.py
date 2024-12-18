#Ce microservice est conçu pour gérer des notifications en temps réel et les diffuser aux clients connectés via WebSocket
from flask import Flask, jsonify, request
from flask_socketio import SocketIO, emit
from flask_cors import CORS
from flask import render_template

# Initialiser Flask et ses extensions
app = Flask(__name__)
CORS(app)  # Autorise les requêtes Cross-Origin
app.config['SECRET_KEY'] = '7b250dcd0a4e5288b0724f36cb5c4d406d5d4ebf84fa844512f0cf03db535d69'

# Initialiser SocketIO
socketio = SocketIO(app, cors_allowed_origins="*")

# Liste pour stocker les notifications
notifications = []

# Endpoint d'accueil
@app.route('/')
def home():
    return render_template("index.html") 

# Endpoint pour envoyer une notification via HTTP
@app.route('/notifications', methods=['POST'])
def send_notification():
    data = request.json
    notifications.append(data)
    # Envoyer une notification à tous les clients connectés via WebSocket
    emit('receive_notification', data, broadcast=True, namespace='/')
    return jsonify({"status": "Notification sent!", "notification": data}), 201

# Endpoint pour récupérer l'historique des notifications
@app.route('/notifications', methods=['GET'])
def get_notifications():
    return jsonify({"notifications": notifications}), 200



# Démarrage de l'application
if __name__ == '__main__':
    socketio.run(app, host='0.0.0.0', port=5000, debug=True)
