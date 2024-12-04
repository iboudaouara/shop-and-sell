<?php

// Récupérer les informations de connexion à partir des variables d'environnement
$serverName = getenv('MYSQL_HOST') ?: 'database';
$database = getenv('MYSQL_DATABASE') ?: 'docker';
$username = getenv('MYSQL_USER') ?: 'docker';
$password = getenv('MYSQL_PASSWORD') ?: 'docker';

try {

    // Créer une nouvelle instance de PDO pour la connexion à la base de données MySQL
    $conn = new PDO("mysql:host=$serverName;dbname=$database", $username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    echo "Connected successfully";
} 

catch(PDOException $e) {
    
    // En cas d'erreur de connexion, enregistrer le message d'erreur et arrêter l'exécution
    error_log("Connection failed: " . $e->getMessage());
    die("Connection failed: " . $e->getMessage());
}
?>
