// Se connecter au serveur WebSocket Flask via Socket.IO
const socket = io('http://localhost:5000');

// Réception d'une notification depuis le serveur Flask
socket.on('receive_notification', function(data) {
    console.log('Notification reçue:', data);
    displayNotification(data.message);  
});

// Fonction pour afficher la notification dans l'interface
function displayNotification(message) {
    const notificationList = document.getElementById('notifications-list');

    // Créer un nouvel élément pour la notification
    const notification = document.createElement('div');
    notification.classList.add('notification');
    
    const notificationMessage = document.createElement('p');
    notificationMessage.textContent = message;

    // Ajouter le message à la notification
    notification.appendChild(notificationMessage);

    // Ajouter la notification au début de la liste
    notificationList.insertBefore(notification, notificationList.firstChild);

    // Si la liste contient trop de notifications, supprimer la plus ancienne
    if (notificationList.children.length > 15) {
        notificationList.removeChild(notificationList.lastChild);
    }
}
