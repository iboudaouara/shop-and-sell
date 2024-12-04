<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

// Informations de connexion
$serverName = 'database'; // Nom d'hôte MySQL dans docker-compose.yml
$database = 'ProjetTch099';
$username = 'docker';
$password = 'docker';

try {
    // Connexion à la base de données MySQL
    $conn = new PDO("mysql:host=$serverName;dbname=$database", $username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    echo "Connected successfully";
} catch(PDOException $e) {
    echo "Connection failed: " . $e->getMessage();
}
?>
