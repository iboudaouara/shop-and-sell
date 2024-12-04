<?php

error_reporting(E_ALL);
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
ob_start();

error_log("Requête reçue : " . $_SERVER['REQUEST_URI']);

require_once __DIR__ . '/router.php';
require_once __DIR__ . '/routes.php';

echo "Bienvenue dans le container TCH099!";
?>