<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Aide - Site de Vente en Ligne</title>
    <link rel="stylesheet" href="aide.css">

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
    <main>
    <section class="faq">
        <h2>Foire Aux Questions (FAQ)</h2>
        <div class="faq-item">
            <h3>Comment créer un compte ?</h3>
            <p>Pour créer un compte, cliquez sur "Connexion" en haut de la page, ensuite vous devez
                sélectionner le bouton créer un compte et suivez les instructions pour vous inscrire en remplissant le formulaire d'inscription.</p>
        </div>
        <div class="faq-item">
            <h3>Comment passer une commande ?</h3>
            <p>Pour passer une commande, vous devez contacter le vendeur sur le numéro de téléphone présent dans son annonce et prendre 
                rendez-vous avec lui afin de venir récupérer l'article ou les articles que vous voulez et payer votre commande.</p>
        </div>
        <div class="faq-item">
            <h3>Quels modes de paiement acceptez-vous ?</h3>
            <p>Nous n'acceptons aucune méthode de paiement en ligne sécurisées, vous devez vous déplacer chez le vendeur et négocier directement avec lui
                pour le paiement, nous ne sommes pas responsables des paiements entre les acheteurs et les vendeurs.</p>
        </div>
        <div class="faq-item">
            <h3>Comment réinitialiser mon mot de passe si je l'ai oublié ?</h3>
            <p>Si vous avez oublié votre mot de passe, vous pouvez réinitialiser votre mot de passe en suivant ces étapes :</p>
            <ol>
                <li>Allez sur la page de <a href="motdepasse-oublie.php">réinitialisation de mot de passe</a>.</li>
                <li>Entrez votre adresse email enregistrée dans le champ prévu.</li>
                <li>Vous recevrez un email contenant un lien de réinitialisation.</li>
                <li>Cliquez sur le lien et suivez les instructions pour choisir un nouveau mot de passe sécurisé.</li>
            </ol>
        </div>
    </section>
        <section class="contact-info">
            <h2>Informations de Contact</h2>
            <p>Si vous avez besoin d'aide supplémentaire, vous pouvez nous contacter par les moyens suivants :</p>
            <ul>
                <li>Email : shop.sell.soutien@gmail.com</li>
                <li>Téléphone : +1 438-815-9338</li>
                <li>Adresse : 123 Rue de la Vente, Montréal, Canada</li>
                <li>Nous sommes disponible à vous répondre 24h sur 24, 7 jours sur 7</li>
            </ul>
        </section>


    </main>

    <footer>
        <p>&copy; 2024 Shop&Sell. Tous droits réservés.</p>
        <a href="conditions.php">Conditions d'utilisation</a> |
        <a href="confidentialite.php">Avis de confidentialité</a> |
        <a href="publicites.php">Publicités</a>
    </footer>
    <script src="auth.js"></script>
    <script src="search.js"></script>

</body>

</html>