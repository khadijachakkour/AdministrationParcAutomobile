from flask import Flask, jsonify, request
from flask_socketio import SocketIO, emit
from flask_cors import CORS
from flask import render_template
from flask_sqlalchemy import SQLAlchemy

# Initialiser Flask et ses extensions
app = Flask(__name__)
CORS(app)

# Configuration de la base de données MySQL
app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql://root@localhost/notification_db'  
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
db = SQLAlchemy(app)

# Initialiser SocketIO
socketio = SocketIO(app, cors_allowed_origins="*")

# Modèle Notification
class Notification(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    message = db.Column(db.String(255), nullable=False)
    timestamp = db.Column(db.DateTime, default=db.func.current_timestamp())

    def __init__(self, message):
        self.message = message

# Créer la base de données si elle n'existe pas
with app.app_context():
    db.create_all()

# Endpoint d'accueil
@app.route('/')
def home():
    return render_template("index.html")

# Endpoint pour envoyer une notification via HTTP
@app.route('/notifications', methods=['POST'])
def send_notification():
    data = request.json
    message = data.get('message')
    
    # Sauvegarder la notification dans la base de données
    notification = Notification(message=message)
    db.session.add(notification)
    db.session.commit()

    # Envoyer une notification à tous les clients connectés via WebSocket
    emit('receive_notification', data, broadcast=True, namespace='/')
    
    return jsonify({"status": "Notification sent!", "notification": data}), 201

# Endpoint pour récupérer l'historique des notifications
@app.route('/notifications', methods=['GET'])
def get_notifications():
    notifications = Notification.query.all()
    notifications_list = [{'id': n.id, 'message': n.message, 'timestamp': n.timestamp} for n in notifications]
    return jsonify({"notifications": notifications_list}), 200

# Démarrage de l'application
if __name__ == '__main__':
    socketio.run(app, host='0.0.0.0', port=5000, debug=True)
 