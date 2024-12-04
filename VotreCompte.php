<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Se connecter - Shop&Sell</title>
    <link rel="stylesheet" href="votreCompte.css"> 
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
</head>
<body>
    <header>
        <div class="header-container">
            <a href="accueil.php" class="logo">Shop <img class="img_logo" src="Image/image_logo.png" alt="Image du logo"> Sell</a>
            <div class="search-bar">
                <input type="text" placeholder="Rechercher des produits">
                <button type="button">Rechercher</button>
            </div>
            <a href="votreCompte.php" class="header-links">Votre compte</a>
            <a href="aide.php" class="header-links">Aide</a>
            <!--<a href="#" class="header-links">Communication</a>-->
        </div>
    </header>

    <div class="container">
        <div class="form-container sign-in-container">
            <form action="traitement_connexion.php" method="POST">
                <h1>Se connecter</h1>
                <div class="social-container">
                    <a href="#" class="social"><i class="fab fa-google"></i></a>
                    <a href="#" class="social"><i class="fab fa-facebook-f"></i></a>
                    <a href="#" class="social"><i class="fab fa-github"></i></a>
                    <a href="#" class="social"><i class="fab fa-linkedin-in"></i></a>
                </div>
                <span>ou utilisez votre compte e-mail</span>
                <input type="text" id="email_or_phone" name="email_or_phone" placeholder="Adresse électronique ou numéro de téléphone mobile" required>
                <input type="password" id="password" name="password" placeholder="Mot de passe" required>
                <a href="#" class="LostPassword">Mot de passe oublié ?</a>
                <button type="submit">Continuer</button>
                <p>En continuant, vous acceptez les <a href="conditions.php">Conditions d'utilisation</a> et la <a href="confidentialite.html">Déclaration de confidentialité</a>.</p>
            </form>
        </div>
        <div class="form-container sign-up-container">
            <form action="#">
                <h1>Créer un compte</h1>
                <div class="social-container">
                    <a href="#" class="social"><i class="fab fa-google"></i></a>
                    <a href="#" class="social"><i class="fab fa-facebook-f"></i></a>
                    <a href="#" class="social"><i class="fab fa-github"></i></a>
                    <a href="#" class="social"><i class="fab fa-linkedin-in"></i></a>
                </div>
                <span>ou utilisez votre email pour vous inscrire</span>
                <input type="text" placeholder="Nom" />
                <input type="email" placeholder="Email" />
                <input type="password" placeholder="Mot de passe" />
                <button>S'inscrire</button>
            </form>
        </div>
        <div class="overlay-container">
            <div class="overlay">
                <div class="overlay-panel overlay-left">
                    <h1>Bon retour!</h1>
                    <p>Pour rester connecté avec nous, veuillez vous connecter avec vos informations personnelles</p>
                    <button class="ghost" id="signIn">Se connecter</button>
                </div>
                <div class="overlay-panel overlay-right">
                    <h1>Bonjour cher Client!</h1>
                    <p>Entrez vos informations personnelles et commencez vos achats avec nous</p>
                    <button class="ghost" id="signUp">S'inscrire</button>
                </div>
            </div>
        </div>
    </div>
    <footer>
        <p>&copy; 2024 eVente. Tous droits réservés.</p>
        <a href="conditions.php">Conditions d'utilisation</a> |
        <a href="confidentialite.php">Avis de confidentialité</a> |
        <a href="publicites.php">Publicités</a>
    </footer>
    <script src="VotreCompte.js"></script>
</body>
</html>
