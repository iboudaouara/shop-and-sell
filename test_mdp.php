<?php
$password = "motdepasse123";
$hash = password_hash($password, PASSWORD_DEFAULT);
echo "Hash: $hash\n";

// Simuler les données qui seraient normalement dans $data et $user
$data = ['password' => $password];
$user = ['mot_de_passe' => $hash];

error_log("Input password: " . $data['password']);
error_log("Stored hash: " . $user['mot_de_passe']);

$verify = password_verify($data['password'], $user['mot_de_passe']);
echo "Verification: " . ($verify ? "Success" : "Failure") . "\n";
?>