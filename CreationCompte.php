<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Créer un compte - Shop&Sell</title>
    <link rel="stylesheet" href="creationCompte.css">
    <link rel="stylesheet" href="accueil.css">
</head>

<body>
    <header>
    <div class="header-container">
        <a href="accueil.php" class="logo">Shop <img class="img_logo" src="Image/image_logo.png" alt="Image du logo"> Sell</a>
        <div class="search-bar">
            <input type="text" id="searchInput" placeholder="Rechercher des produits">
            <button type="button" id="searchButton">Rechercher</button>
        </div>
        <a href="login.php" id="loginLink" class="header-links">Connexion</a>
        <a href="profil.php" id="profileLink" class="header-links" style="display: none;">Profil</a>
        <a href="#" id="logoutButton" class="header-links" style="display: none;">Déconnexion</a>
        <a href="sell.php" id="sellLink" class="header-links" style="display: none;">Vendre</a>
        <a href="aide.php" class="header-links">Aide</a>
    </div>
    </header>

    <div class="container">
        <div class="form-container">
            <form>
                <h1>Créer un compte</h1>


                <input type="text" name="username" placeholder="Nom d'utilisateur" required>
                <input type="text" name="prenom" placeholder="Prénom" required>
                <input type="text" name="nom" placeholder="Nom" required>
                <input type="email" id="email" name="email" placeholder="Adresse électronique" required>
                <input type="password" id="password" name="password" placeholder="Mot de passe" required>
                <input type="password" id="confirm_password" name="confirm_password" placeholder="Confirmez le mot de passe" required>


                <input type="text" name="telephone" placeholder="Numéro de téléphone" required>

                
                <select name="question_securite1" required>
                    <option value="">Sélectionnez une question de sécurité 1</option>
                    <option value="Quel est le nom de votre premier animal de compagnie ?">Quel est le nom de votre premier animal de compagnie ?</option>
                    <option value="Quel est le nom de jeune fille de votre mère ?">Quel est le nom de jeune fille de votre mère ?</option>
                    <option value="Quel est votre film préféré ?">Quel est votre film préféré ?</option>
                </select>
                <input type="text" name="reponse_securite1" placeholder="Réponse" required>

                <select name="question_securite2" required>
                    <option value="">Sélectionnez une question de sécurité 2</option>
                    <option value="Quel est le nom de votre école primaire ?">Quel est le nom de votre école primaire ?</option>
                    <option value="Quel est le nom de votre meilleur ami d'enfance ?">Quel est le nom de votre meilleur ami d'enfance ?</option>
                    <option value="Quel est le modèle de votre première voiture ?">Quel est le modèle de votre première voiture ?</option>
                </select>
                <input type="text" name="reponse_securite2" placeholder="Réponse" required>

                <button type="submit">S'inscrire</button>
                <p>En continuant, vous acceptez les <a href="conditions.php">Conditions d'utilisation</a> et la <a href="confidentialite.php">Déclaration de confidentialité</a>.</p>
            </form>
            <div id="message-container"></div>
        </div>
    </div>
    <footer>
        <p>&copy; 2024 Shop&Sell. Tous droits réservés.</p>
        <a href="conditions.php">Conditions d'utilisation</a> |
        <a href="confidentialite.php">Avis de confidentialité</a> |
        <a href="publicites.php">Publicités</a>
    </footer>


    <script src="creationCompte.js"></script>
    <script src="search.js"></script>

</body>

</html>