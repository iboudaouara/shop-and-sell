<?php
require_once __DIR__ . '/router.php';

// Fonction de connexion à la base de données SQL Server
function connectToDatabase()
{
    // Informations de connexion
    $serverName = 'database'; 
    $database = 'ProjetTch099';
    $username = 'docker';
    $password = 'docker';
    
    try {
        // Créer une nouvelle instance de PDO pour la connexion à la base de données MySQL
        $conn = new PDO("mysql:host=$serverName;dbname=$database", $username, $password);
        $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        return $conn; // Return the PDO instance
    } 
    catch(PDOException $e) {
        // En cas d'erreur de connexion, enregistrer le message d'erreur et arrêter l'exécution
        error_log("Connection failed: " . $e->getMessage());
        die("Connection failed: " . $e->getMessage());
    }
    
}

$pdo = connectToDatabase();
//global $pdo; // Déclare la variable $pdo globale


// ##################################################
// Routes statiques pour l'application
// ##################################################

get('/', 'accueil.php');
get('/accueil.php', 'accueil.php');
get('/votreCompte.php', 'votreCompte.php');
get('/articles.php', 'articles.php');
get('/login.php', 'login.php');
get('/aide.php', 'aide.php');
get('/sell.php', 'sell.php');
get('/produits.php', 'produits.php');
get('/profil.php', 'profil.php');
get('/publicites.php', 'publicites.php');
get('/confidentialite.php', 'confidentialite.php');
get('/conditions.php', 'conditions.php');
get('/creationCompte.php', 'creationCompte.php');
get('/motdepasse-oublie.php', 'motdepasse-oublie.php');
get('/CompteVendeur.php', 'CompteVendeur.php');


// ##################################################
// Routes pour l'API REST 
// ##################################################

get('/api/test', function() {
    echo json_encode(['message' => 'Test successful']);
});

get('/api/articles', function(){
    global $pdo;
    try {
        $stmt = $pdo->query("SELECT * FROM Articles");
        $articles = $stmt->fetchAll(PDO::FETCH_ASSOC);
        echo json_encode($articles);
    } 

    catch (PDOException $e) {
        http_response_code(500);
        echo json_encode(["message" => "Error fetching data", "error" => $e->getMessage()]);
    }
});

get('/api/utilisateurs', function(){
    global $pdo;
    try {
        $stmt = $pdo->query("SELECT * FROM Utilisateurs");
        $utilisateurs = $stmt->fetchAll(PDO::FETCH_ASSOC);
        echo json_encode($utilisateurs);
    } 
    
    catch (PDOException $e) {
        http_response_code(500);
        echo json_encode(["message" => "Error fetching data", "error" => $e->getMessage()]);
    }
});

get('/api/articles/categories/$type', function($type){
    global $pdo;
    try {
        $type = urldecode($type);
        $stmt = $pdo->prepare("SELECT * FROM Articles WHERE type = :type");
        $stmt->execute([':type' => $type]);
        $articles = $stmt->fetchAll(PDO::FETCH_ASSOC);
        echo json_encode($articles);
    } catch (PDOException $e) {
        http_response_code(500);
        echo json_encode(["message" => "Error fetching data", "error" => $e->getMessage()]);
    }
});

get('/api/articles/categories_limit/$type', function($type){
    global $pdo;
    try {
        // Correction de la requête SQL pour MySQL
        $stmt = $pdo->prepare("SELECT * FROM Articles WHERE type = :type LIMIT 4");
        $stmt->execute([':type' => $type]);
        $articles = $stmt->fetchAll(PDO::FETCH_ASSOC);
        echo json_encode($articles);
    } 

    catch (PDOException $e) {
        http_response_code(500);
        echo json_encode(["message" => "Error fetching data", "error" => $e->getMessage()]);
    }
});


get('/api/articles/$id', function($id){
    global $pdo;
    try {
        $stmt = $pdo->prepare("SELECT * FROM Articles WHERE id = :id");
        $stmt->execute([':id' => $id]);
        $articles = $stmt->fetch(PDO::FETCH_ASSOC);
        if ($articles) {
            echo json_encode($articles);
        } else {
            http_response_code(404);
            echo json_encode(["message" => "Articles not found"]);
        }
    } catch (PDOException $e) {
        http_response_code(500);
        echo json_encode(["message" => "Error fetching data", "error" => $e->getMessage()]);
    }
});


get('/api/articles/trier/$type', function($type) {
    global $pdo;

    // Define valid columns and sort options
    $validColumns = ['nom']; // Example valid columns; add others as needed

    // Sanitize and validate inputs
    $type = strtolower($type); // Convert to lowercase for consistency

    try {
        // Construct the SQL query with the validated inputs
        $query = "SELECT * FROM Articles ORDER BY $type";
        $stmt = $pdo->query($query); // Use query() method since no parameters are involved

        $articles = $stmt->fetchAll(PDO::FETCH_ASSOC);
        echo json_encode($articles);
    } catch (PDOException $e) {
        http_response_code(500);
        echo json_encode(["message" => "Error fetching data", "error" => $e->getMessage()]);
    }
});


get('/api/articles/trier/$type/$option', function($type, $option) {
    global $pdo;

    // Define valid columns and sort options
    $validColumns = ['prix', 'nom']; // Example valid columns; add others as needed
    $validOptions = ['ASC', 'DESC', ''];    // Valid sort directions

    // Sanitize and validate inputs
    $type = strtolower($type); // Convert to lowercase for consistency
    $option = strtoupper($option); // Convert to uppercase

    if (!in_array($type, $validColumns) || !in_array($option, $validOptions)) {
        http_response_code(400); // Bad Request
        echo json_encode(["message" => "Invalid sorting parameters"]);
        return;
    }

    try {
        // Construct the SQL query with the validated inputs
        $query = "SELECT * FROM Articles ORDER BY $type $option";
        $stmt = $pdo->query($query); // Use query() method since no parameters are involved

        $articles = $stmt->fetchAll(PDO::FETCH_ASSOC);
        echo json_encode($articles);
    } catch (PDOException $e) {
        http_response_code(500);
        echo json_encode(["message" => "Error fetching data", "error" => $e->getMessage()]);
    }
});

//get pour articles par id vendeur 
get('/api/utilisateurs/$id', function($id) {
    global $pdo;

    $vendorId = intval($id);

    if ($vendorId === 0) {
        http_response_code(400);
        echo json_encode(['error' => 'Invalid vendor ID']);
        return;
    }

    try {
        // Obtenir les informations du vendeur
        $stmt = $pdo->prepare("SELECT * FROM Utilisateurs WHERE id = :vendorId");
        $stmt->bindParam(':vendorId', $vendorId, PDO::PARAM_INT);
        $stmt->execute();
        $vendor = $stmt->fetch(PDO::FETCH_ASSOC);

        if (!$vendor) {
            http_response_code(404);
            echo json_encode(['error' => 'Vendor not found']);
            return;
        }

        // Obtenir les articles du vendeur
        $stmt = $pdo->prepare("SELECT * FROM Articles WHERE utilisateur_id = :vendorId");
        $stmt->bindParam(':vendorId', $vendorId, PDO::PARAM_INT);
        $stmt->execute();
        $articles = $stmt->fetchAll(PDO::FETCH_ASSOC);

        echo json_encode([
            'vendorOrUser' => $vendor,
            'articles' => $articles
        ]);
    } catch(PDOException $e) {
        http_response_code(500);
        echo json_encode(['error' => $e->getMessage()]);
    }
});

// Route pour récupérer le id de l'utilisateur
get('/api/utilisateurs/{id}', function($id) {
    global $pdo;
    try {
        $stmt = $pdo->prepare("SELECT * FROM Utilisateurs WHERE id = ?");
        $stmt->execute([$id]);
        $utilisateur = $stmt->fetch(PDO::FETCH_ASSOC);
        if ($utilisateur) {
            echo json_encode($utilisateur);
        } else {
            http_response_code(404);
            echo json_encode(["message" => "Utilisateur non trouvé"]);
        }
    } catch (PDOException $e) {
        http_response_code(500);
        echo json_encode(["message" => "Erreur lors de la récupération des données", "error" => $e->getMessage()]);
    }
});

// Route pour ajouter un articlen à la base de donnée
post('/api/articles', function() {
    global $pdo;
    
    if (!empty($_POST['utilisateur_id']) && !empty($_POST['nom']) && !empty($_POST['prix']) 
        && !empty($_POST['quantite']) && !empty($_POST['type']) && !empty($_FILES['image'])) {
        try {
            // Vérifier le type de fichier
            $allowedTypes = ['image/jpeg', 'image/png', 'image/gif'];
            $fileType = $_FILES['image']['type'];
            if (!in_array($fileType, $allowedTypes)) {
                throw new Exception("Type de fichier non autorisé. Veuillez utiliser JPEG, PNG ou GIF.");
                    
            }

            // Vérifier la taille du fichier (par exemple, limite à 5MB)
            if ($_FILES['image']['size'] > 15000000) {
                throw new Exception("Le fichier est trop volumineux. La taille maximum est de 5MB.");
            }

            // Générer un nom de fichier unique
            $imageFileName = uniqid() . '_' . basename($_FILES['image']['name']);
            $uploadPath = __DIR__ . '/uploads/' . $imageFileName;

            // Déplacer le fichier téléchargé vers le dossier uploads
            if (!move_uploaded_file($_FILES['image']['tmp_name'], $uploadPath)) {
                throw new Exception("Échec du téléchargement de l'image.");
            }

            // Créer l'URL de l'image
            $imageUrl = '/uploads/' . $imageFileName;
            
            // Préparer la requête d'insertion
            $stmt = $pdo->prepare("INSERT INTO Articles (utilisateur_id, nom, description, prix, quantite, type, nom_utilisateur, url_image) 
                                   VALUES (:utilisateur_id, :nom, :description, :prix, :quantite, :type, :nom_utilisateur, :url_image)");
            
            $stmt->bindValue(':utilisateur_id', $_POST['utilisateur_id'], PDO::PARAM_INT);
            $stmt->bindValue(':nom', $_POST['nom'], PDO::PARAM_STR);
            $stmt->bindValue(':description', $_POST['description'], PDO::PARAM_STR);
            $stmt->bindValue(':prix', $_POST['prix'], PDO::PARAM_STR);
            $stmt->bindValue(':quantite', $_POST['quantite'], PDO::PARAM_INT);
            $stmt->bindValue(':type', $_POST['type'], PDO::PARAM_STR);
            $stmt->bindValue(':nom_utilisateur', $_POST['nom_utilisateur'], PDO::PARAM_STR);
            $stmt->bindValue(':url_image', $imageUrl, PDO::PARAM_STR);

            $stmt->execute();

            http_response_code(201);
            echo json_encode(["message" => "Article ajouté avec succès", "success" => true]);
        } catch (Exception $e) {
            http_response_code(500);
            echo json_encode(["message" => "Erreur lors de l'ajout de l'article", "error" => $e->getMessage(), "success" => false]);
        }
    } else {
        http_response_code(400);
        echo json_encode(["message" => "Données invalides", "success" => false]);
    }
});


// Route pour récupérer les avis d'un vendeur
get('/api/commentaires', function() {
    global $pdo;
    $vendeurId = $_GET['vendeur_id'] ?? null;

    if ($vendeurId) {
        try {
            $stmt = $pdo->prepare("SELECT utilisateurs.nom_utilisateur AS username, avis.commentaire FROM avis INNER JOIN utilisateurs ON avis.utilisateur_id = utilisateurs.id WHERE avis.vendeur_id = :vendeur_id");
            $stmt->execute([':vendeur_id' => $vendeurId]);
            $commentaires = $stmt->fetchAll(PDO::FETCH_ASSOC);
            
            http_response_code(200);
            echo json_encode($commentaires);
        } catch (PDOException $e) {
            http_response_code(500);
            echo json_encode(["message" => "Erreur lors de la récupération des commentaires", "error" => $e->getMessage()]);
        }
    } else {
        http_response_code(400);
        echo json_encode(["message" => "ID du vendeur manquant"]);
    }
});



//methode post pour ajouter commentaires dans la table Avis
post('/api/commentaires', function() {
    global $pdo;
    $data = json_decode(file_get_contents("php://input"), true);
    
    if (!empty($data['vendeur_id']) && !empty($data['utilisateur_id']) && !empty($data['commentaire'])) {
        try {
            $stmt = $pdo->prepare("INSERT INTO Avis (vendeur_id, utilisateur_id, commentaire) VALUES (:vendeur_id, :utilisateur_id, :commentaire)");
            $stmt->execute([
                ':vendeur_id' => $data['vendeur_id'],
                ':utilisateur_id' => $data['utilisateur_id'],
                ':commentaire' => $data['commentaire']
            ]);
            http_response_code(201);
            echo json_encode(["message" => "Commentaire ajouté avec succès"]);
        } catch (PDOException $e) {
            http_response_code(500);
            echo json_encode(["message" => "Erreur lors de l'ajout du commentaire", "error" => $e->getMessage()]);
        }
    } else {
        http_response_code(400);
        echo json_encode(["message" => "Entrée invalide"]);
    }
});

//route pour supprimer un commentaires laisser par un utilisateur
// Route pour supprimer tous les commentaires d'un utilisateur spécifique pour un vendeur spécifique
delete('/api/commentaires', function() {
    global $pdo;

    $vendeur_id = $_GET['vendeur_id'] ?? null;
    $utilisateur_id = $_GET['utilisateur_id'] ?? null;

    if ($vendeur_id && $utilisateur_id) {
        try {
            $stmt = $pdo->prepare("DELETE FROM Avis WHERE vendeur_id = :vendeur_id AND utilisateur_id = :utilisateur_id");
            $stmt->execute([':vendeur_id' => $vendeur_id, ':utilisateur_id' => $utilisateur_id]);

            if ($stmt->rowCount() > 0) {
                http_response_code(200);
                echo json_encode(["message" => "Commentaires supprimés avec succès"]);
            } else {
                http_response_code(404);
                echo json_encode(["message" => "Aucun commentaire trouvé pour cet utilisateur et ce vendeur"]);
            }
        } catch (PDOException $e) {
            http_response_code(500);
            echo json_encode(["message" => "Erreur lors de la suppression des commentaires", "error" => $e->getMessage()]);
        }
    } else {
        http_response_code(400);
        echo json_encode(["message" => "ID du vendeur ou de l'utilisateur invalide"]);
    }
});



// POST pour l'authentification utilisateur
post('/api/login', function () use ($pdo) {
    loginUser($pdo);
});

// POST pour l'inscription utilisateur
post('/api/register', function () use ($pdo) {
    registerUser($pdo);
});

post('/api/check_email', function () use ($pdo) {
    checkEmail($pdo);
});

// Route pour vérifier les réponses de sécurité
post('/api/check_security_answers', function () use ($pdo) {
    checkSecurityAnswers($pdo);
});

post('/api/check_security_answers/mobile', function () use ($pdo) {
    checkSecurityAnswersMobile($pdo);
});

post('/api/reset_password', function () use ($pdo) {  
    resetPassword($pdo);
});

post('/api/reset_password/mobile', function () use ($pdo) {  
    resetPasswordMobile($pdo);
});

// ##################################################
// Fonctions pour gérer les requêtes API
// ##################################################

// Route pour la connexion
function loginUser($pdo)
{
    $data = json_decode(file_get_contents('php://input'), true);

    // Vérifier si toutes les données requises sont présentes
    if (!$data || !isset($data['email'], $data['password'])) {
        http_response_code(400);
        echo json_encode(['error' => 'Invalid data provided']);
        return;
    }

    // Vérifier les informations d'identification dans la base de données
    try {
        $stmt = $pdo->prepare("SELECT * FROM Utilisateurs WHERE email = ?");
        $stmt->execute([$data['email']]);
        $user = $stmt->fetch(PDO::FETCH_ASSOC);

        if ($user && password_verify($data['password'], $user['mot_de_passe'])) {
            // Utilisateur authentifié avec succès
            http_response_code(200);
            echo json_encode(['message' => 'Login successful', 'user' => [
                'id' => $user['id'],
                'username' => $user['nom_utilisateur'],
                'email' => $user['email'],
            ]]);
        } else {
            // Identifiants incorrects
            http_response_code(401);
            echo json_encode(['error' => 'Invalid credentials']);
        }
    } catch (PDOException $e) {
        http_response_code(500);
        echo json_encode(['error' => 'Failed to login: ' . $e->getMessage()]);
    }
}

// Route pour la création du compte
function registerUser($pdo)
{
    // Récupérer les données JSON de la requête
    $data = json_decode(file_get_contents('php://input'), true);

    // Vérifier si toutes les données requises sont présentes
    if (!$data || !isset($data['nom_utilisateur'], $data['email'], $data['password'],$data['prenom'],$data['nom'] , $data['telephone'],$data['question_securite1'], $data['reponse_securite1'], $data['question_securite2'], $data['reponse_securite2'])) {
        http_response_code(400);
        echo json_encode(['error' => 'data invalide']);
        return;
    }

    // Vérifier si l'utilisateur existe déjà
    try {
        $stmt = $pdo->prepare("SELECT * FROM Utilisateurs WHERE email = ?");
        $stmt->execute([$data['email']]);
        $user = $stmt->fetch(PDO::FETCH_ASSOC);

        if ($user) {
            // Utilisateur déjà enregistré
            http_response_code(400);
            echo json_encode(['error' => 'Utilisateur existe déjà']);
            return;
        }

        // Hasher le mot de passe avant de l'insérer dans la base de données
        $hashed_password = password_hash($data['password'], PASSWORD_DEFAULT);

        // Insérer un nouvel utilisateur dans la base de données
        $sql = "INSERT INTO Utilisateurs (nom_utilisateur, email, mot_de_passe, prenom,nom ,telephone, question_securite1, reponse_securite1, question_securite2, reponse_securite2) VALUES (?, ?, ?, ?,?, ?, ?, ?, ?, ?)";
        $stmt = $pdo->prepare($sql);
        $stmt->execute([
            $data['nom_utilisateur'],
            $data['email'],
            $hashed_password,
            $data['prenom'],
            $data['nom'],
            $data['telephone'],
            $data['question_securite1'],
            $data['reponse_securite1'],
            $data['question_securite2'],
            $data['reponse_securite2']
        ]);
        // récuperer le id du nouvel utilisateur ajouté
        $newUserId = $pdo->lastInsertId();

        http_response_code(201);
        echo json_encode(['message' => 'User registered successfully',
                        'id' => $newUserId]);
    } catch (PDOException $e) {
        http_response_code(500);
        echo json_encode(['error' => 'Failed to register user: ' . $e->getMessage()]);
    }
}


// Route pour récupérer les articles ayant un nom semblable à la recherche
get('/api/searcharticle', function(){
    global $pdo;
    $searchQuery = $_GET['search'] ?? '';
    
    if ($searchQuery) {
        try {
            // Normalisation des accents pour la recherche
            $normalizedSearchQuery = removeAccents($searchQuery);

            // Préparer la requête avec la fonction REPLACE pour enlever les accents dans la comparaison
            $stmt = $pdo->prepare("
                SELECT * FROM Articles 
                WHERE REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(nom, 'é', 'e'), 'è', 'e'), 'ê', 'e'), 'à', 'a'), 'ù', 'u'), 'ç', 'c') LIKE :searchQuery
            ");
            
            // Ajouter des variantes supplémentaires si nécessaire pour plus de caractères accentués
            $stmt->execute([':searchQuery' => '%' . $normalizedSearchQuery . '%']);
            $articles = $stmt->fetchAll(PDO::FETCH_ASSOC);
            echo json_encode($articles);
        } catch (PDOException $e) {
            http_response_code(500);
            echo json_encode(["message" => "Error fetching data", "error" => $e->getMessage()]);
        }
    } else {
        http_response_code(400);
        echo json_encode(["message" => "No search query provided"]);
    }
});

// Fonction pour supprimer les accents
function removeAccents($string) {
    $search = array('À', 'Á', 'Â', 'Ã', 'Ä', 'Å', 'Æ', 'Ç', 'È', 'É', 'Ê', 'Ë', 'Ì', 'Í', 'Î', 'Ï', 'Ñ', 'Ò', 'Ó', 'Ô', 'Õ', 'Ö', 'Ø', 'Ù', 'Ú', 'Û', 'Ü', 'Ý', 'Þ', 'à', 'á', 'â', 'ã', 'ä', 'å', 'æ', 'ç', 'è', 'é', 'ê', 'ë', 'ì', 'í', 'î', 'ï', 'ñ', 'ò', 'ó', 'ô', 'õ', 'ö', 'ø', 'ù', 'ú', 'û', 'ü', 'ý', 'þ', 'ÿ');
    $replace = array('A', 'A', 'A', 'A', 'A', 'A', 'AE', 'C', 'E', 'E', 'E', 'E', 'I', 'I', 'I', 'I', 'N', 'O', 'O', 'O', 'O', 'O', 'O', 'U', 'U', 'U', 'U', 'Y', 'TH', 'a', 'a', 'a', 'a', 'a', 'a', 'ae', 'c', 'e', 'e', 'e', 'e', 'i', 'i', 'i', 'i', 'n', 'o', 'o', 'o', 'o', 'o', 'o', 'u', 'u', 'u', 'u', 'y', 'th', 'y');
    return str_replace($search, $replace, $string);
}

// Fonction pour vérifier l'email et récupérer les questions de sécurité
function checkEmail($pdo)
{
    $data = json_decode(file_get_contents('php://input'), true);

    if (!isset($data['email'])) {
        http_response_code(400);
        echo json_encode(['error' => 'Email non fourni']);
        return;
    }

    $stmt = $pdo->prepare("SELECT question_securite1, question_securite2 FROM Utilisateurs WHERE email = ?");
    $stmt->execute([$data['email']]);
    $user = $stmt->fetch(PDO::FETCH_ASSOC);

    if ($user) {
        // Sauvegarder l'email dans la session pour l'utiliser plus tard
        session_start();
        $_SESSION['email'] = $data['email'];

        http_response_code(200);
        echo json_encode(['questions' => [$user['question_securite1'], $user['question_securite2']]]);
    } else {
        http_response_code(404);
        echo json_encode(['error' => 'Email non trouvé']);
    }
}

// Fonction pour vérifier les réponses aux questions de sécurité
function checkSecurityAnswers($pdo)
{
    session_start();
    $data = json_decode(file_get_contents('php://input'), true);

    if (!isset($data['answers']) || count($data['answers']) != 2) {
        http_response_code(400);
        echo json_encode(['error' => 'Réponses invalides fournies']);
        return;
    }

    if (!isset($_SESSION['email'])) {
        http_response_code(400);
        echo json_encode(['error' => 'Session email non définie']);
        return;
    }

    $stmt = $pdo->prepare("SELECT reponse_securite1, reponse_securite2 FROM Utilisateurs WHERE email = ?");
    $stmt->execute([$_SESSION['email']]);
    $user = $stmt->fetch(PDO::FETCH_ASSOC);

    if ($user && 
        strtolower(trim($data['answers'][0])) === strtolower(trim($user['reponse_securite1'])) && 
        strtolower(trim($data['answers'][1])) === strtolower(trim($user['reponse_securite2']))) {
        http_response_code(200);
        echo json_encode(['message' => 'Réponses de sécurité vérifiées']);
    } else {
        http_response_code(400);
        echo json_encode(['error' => 'Réponses de sécurité invalides']);
    }
}

function checkSecurityAnswersMobile($pdo)
{
    $data = json_decode(file_get_contents('php://input'), true);

    if (!isset($data['answers']) || count($data['answers']) != 2 || !isset($data['email'])) {
        http_response_code(400);
        echo json_encode(['error' => 'Invalid input provided']);
        return;
    }

    $stmt = $pdo->prepare("SELECT reponse_securite1, reponse_securite2 FROM Utilisateurs WHERE email = ?");
    $stmt->execute([$data['email']]);
    $user = $stmt->fetch(PDO::FETCH_ASSOC);

    if ($user && 
        strtolower(trim($data['answers'][0])) === strtolower(trim($user['reponse_securite1'])) && 
        strtolower(trim($data['answers'][1])) === strtolower(trim($user['reponse_securite2']))) {
        http_response_code(200);
        echo json_encode(['message' => 'Security answers verified']);
    } else {
        http_response_code(400);
        echo json_encode(['error' => 'Invalid security answers']);
    }
}

// Route pour réinitialiser le mot de passe
function resetPassword($pdo)
{
    session_start();
    $data = json_decode(file_get_contents('php://input'), true);

    error_log("Reset password attempt for email: " . ($_SESSION['email'] ?? 'Not set'));

    if (!isset($data['newPassword'])) {
        http_response_code(400);
        echo json_encode(['error' => 'Nouveau mot de passe non fourni']);
        error_log("New password not provided in reset attempt");
        return;
    }

    if (!isset($_SESSION['email'])) {
        http_response_code(400);
        echo json_encode(['error' => 'Session email non définie']);
        error_log("Session email not set in reset attempt");
        return;
    }

    $hashedPassword = password_hash($data['newPassword'], PASSWORD_DEFAULT);

    try {
        $pdo->beginTransaction();

        
        $updateStmt = $pdo->prepare("UPDATE Utilisateurs SET mot_de_passe = ? WHERE email = ?");
        $updateStmt->execute([$hashedPassword, $_SESSION['email']]);

        if ($updateStmt->rowCount() > 0) {
            $pdo->commit();
            http_response_code(200);
            echo json_encode(['message' => 'Réinitialisation du mot de passe réussie']);
            error_log("Password successfully reset for user: " . $_SESSION['email']);
        } else {
            throw new Exception('Aucune mise à jour effectuée');
        }
    } catch (Exception $e) {
        $pdo->rollBack();
        http_response_code(500);
        echo json_encode(['error' => 'Échec de la réinitialisation du mot de passe: ' . $e->getMessage()]);
        error_log("Password reset failed for user: " . $_SESSION['email'] . ". Error: " . $e->getMessage());
    }

    try {
        $verifyStmt = $pdo->prepare("SELECT mot_de_passe FROM Utilisateurs WHERE email = ?");
        $verifyStmt->execute([$_SESSION['email']]);
        $updatedUser = $verifyStmt->fetch(PDO::FETCH_ASSOC);
        if ($updatedUser && password_verify($data['newPassword'], $updatedUser['mot_de_passe'])) {
            error_log("Password verification successful after reset for user: " . $_SESSION['email']);
        } else {
            error_log("Password verification failed after reset for user: " . $_SESSION['email']);
        }
    } catch (Exception $e) {
        error_log("Error verifying password after reset: " . $e->getMessage());
    }
}

function resetPasswordMobile($pdo)
{
    // Decode JSON input
    $data = json_decode(file_get_contents('php://input'), true);

    // Log the received request data
    error_log("Reset password attempt for email: " . ($data['email'] ?? 'Not set'));

    // Validate input data
    if (empty($data['email']) || empty($data['newPassword'])) {
        http_response_code(400); // Bad Request
        echo json_encode(['error' => 'Email or new password not provided']);
        error_log("Email or new password not provided in reset attempt");
        return;
    }

    // Hash the new password
    $hashedPassword = password_hash($data['newPassword'], PASSWORD_DEFAULT);

    try {
        // Begin database transaction
        $pdo->beginTransaction();

        // Prepare and execute update statement
        $updateStmt = $pdo->prepare("UPDATE Utilisateurs SET mot_de_passe = ? WHERE email = ?");
        $updateStmt->execute([$hashedPassword, $data['email']]);

        // Check if the update was successful
        if ($updateStmt->rowCount() > 0) {
            $pdo->commit(); // Commit transaction
            http_response_code(200); // OK
            echo json_encode(['message' => 'Password reset successful']);
            error_log("Password successfully reset for user: " . $data['email']);
        } else {
            $pdo->rollBack(); // Rollback transaction
            http_response_code(404); // Not Found
            echo json_encode(['error' => 'No user found with the provided email']);
            error_log("No user found with email: " . $data['email']);
        }
    } catch (Exception $e) {
        // Rollback transaction on exception
        $pdo->rollBack();
        http_response_code(500); // Internal Server Error
        $errorMessage = 'Password reset failed: ' . $e->getMessage();
        echo json_encode(['error' => $errorMessage]);
        error_log($errorMessage);
    }
}

// route pour supprimer un article
delete('/api/articles/$id', function($id) {
    global $pdo;

    try {
        // Vérifiez que l'ID est un nombre entier valide
        if (!is_numeric($id) || $id <= 0) {
            http_response_code(400); // Bad Request
            echo json_encode(["message" => "Invalid article ID"]);
            return;
        }

        // Préparer la requête d'effacement avec condition sur l'id
        $stmt = $pdo->prepare("DELETE FROM Articles WHERE id = :id");
        $stmt->bindParam(':id', $id, PDO::PARAM_INT);
        $stmt->execute();

        if ($stmt->rowCount() > 0) {
            http_response_code(204); // No Content
            echo json_encode(["message" => "Article deleted successfully"]);
        } else {
            http_response_code(404); // Not Found
            echo json_encode(["message" => "Article not found"]);
        }
    } catch (PDOException $e) {
        error_log("Error deleting article: " . $e->getMessage()); // Log error message
        http_response_code(500);
        echo json_encode(["message" => "Error deleting article", "error" => $e->getMessage()]);
    }
});


// Route pour récupérer une image par son ID
get('/api/images/$id', function($id) {
    global $pdo;
    try {
        $stmt = $pdo->prepare("SELECT * FROM Images WHERE ImageID = ?");
        $stmt->execute([$id]);
        $image = $stmt->fetch(PDO::FETCH_ASSOC);
        
        if ($image) {
            header("Content-Type: image/" . $image['FileType']);
            echo $image['ImageData'];
        } else {
            http_response_code(404);
            echo json_encode(["message" => "Image not found"]);
        }
    } catch (PDOException $e) {
        http_response_code(500);
        echo json_encode(["message" => "Error fetching image", "error" => $e->getMessage()]);
    }
});




// Route pour mettre à jour la photo de profil
post('/api/utilisateurs/photo', function() use ($pdo) {
    $data = json_decode(file_get_contents('php://input'), true);
    $id = $data['id'];
    $photo_profil = $data['photo_profil'];

    try {
        $stmt = $pdo->prepare("UPDATE Utilisateurs SET photo_profil = ? WHERE id = ?");
        $stmt->execute([$photo_profil, $id]);
        
        if ($stmt->rowCount() > 0) {
            echo json_encode(['success' => true, 'message' => 'Photo de profil mise à jour']);
        } else {
            echo json_encode(['success' => false, 'message' => 'Aucune mise à jour effectuée']);
        }
    } catch (PDOException $e) {
        http_response_code(500);
        echo json_encode(['success' => false, 'message' => 'Erreur lors de la mise à jour : ' . $e->getMessage()]);
    }
});


post('/api/utilisateurs/update', function() use ($pdo) {
    $data = json_decode(file_get_contents('php://input'), true);
    $id = $data['id'];
    $username = $data['username'];
    $email = $data['email'];
    $phone = $data['phone'];
    $password = $data['password'];
    $prenom = $data['prenom'];
    $nom = $data['nom'];
    $adresse = $data['adresse'];
    $ville = $data['ville'];
    $code_postal = $data['code_postal'];
    $pays = $data['pays'];

    try {
        // Vérifier si le nom d'utilisateur existe déjà
        $stmt = $pdo->prepare("SELECT id FROM Utilisateurs WHERE nom_utilisateur = ? AND id != ?");
        $stmt->execute([$username, $id]);
        if ($stmt->fetch()) {
            echo json_encode(['success' => false, 'message' => 'Nom d\'utilisateur déjà pris']);
            return;
        }

        // Mettre à jour les informations de l'utilisateur
        $stmt = $pdo->prepare("UPDATE Utilisateurs SET nom_utilisateur = ?, email = ?, telephone = ?, prenom = ?, nom = ?, adresse = ?, ville = ?, code_postal = ?, pays = ? WHERE id = ?");
        $stmt->execute([$username, $email, $phone, $prenom, $nom, $adresse, $ville, $code_postal, $pays, $id]);

        // Mettre à jour le mot de passe si fourni
        if (!empty($password)) {
            $hashedPassword = password_hash($password, PASSWORD_DEFAULT);
            $stmt = $pdo->prepare("UPDATE Utilisateurs SET mot_de_passe = ? WHERE id = ?");
            $stmt->execute([$hashedPassword, $id]);
        }

        echo json_encode(['success' => true, 'message' => 'Informations mises à jour avec succès']);
    } catch (PDOException $e) {
        http_response_code(500);
        echo json_encode(['success' => false, 'message' => 'Erreur lors de la mise à jour : ' . $e->getMessage()]);
    }
});



?>