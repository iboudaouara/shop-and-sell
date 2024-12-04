<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profil Utilisateur</title>
    <link rel="stylesheet" href="profil.css">
</head>

<body>
    <header>
        <div class="header-container">
            <a href="accueil.php" class="logo">Shop <img class="img_logo" src="Image/image_logo.png"
                    alt="Image du logo"> Sell</a>
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
        <div class="profile-container">
            <h1>Mon Profil</h1>
            <div class="profile-photo">
                <!--<h2>Photo de profil</h2>-->
                <img id="current-photo" src="image/pdp1.png" alt="Photo de profil actuelle">
            </div>
            <div id="photo-selection" style="display:none;">
                <h2>Choisir une nouvelle photo de profil</h2>
                <div class="photo-options">
                    <img src="image/pdp1.png" alt="Photo 1" onclick="selectPhoto('pdp1.png')">
                    <img src="image/pdp2.png" alt="Photo 2" onclick="selectPhoto('pdp2.png')">
                    <img src="image/pdp3.png" alt="Photo 3" onclick="selectPhoto('pdp3.png')">
                    <img src="image/pdp4.png" alt="Photo 4" onclick="selectPhoto('pdp4.png')">
                    <img src="image/pdp5.png" alt="Photo 5" onclick="selectPhoto('pdp5.png')">
                    <img src="image/pdp6.png" alt="Photo 6" onclick="selectPhoto('pdp6.png')">
                    <img src="image/pdp7.png" alt="Photo 7" onclick="selectPhoto('pdp7.png')">
                    <img src="image/pdp8.png" alt="Photo 8" onclick="selectPhoto('pdp8.png')">
                    <img src="image/pdp9.png" alt="Photo 9" onclick="selectPhoto('pdp9.png')">
                    <img src="image/pdp10.png" alt="Photo 10" onclick="selectPhoto('pdp10.png')">
                    <img src="image/pdp11.png" alt="Photo 11" onclick="selectPhoto('pdp11.png')">
                    <img src="image/pdp12.png" alt="Photo 12" onclick="selectPhoto('pdp12.png')">
                    <img src="image/pdp13.png" alt="Photo 13" onclick="selectPhoto('pdp13.png')">
                </div>
                <button class="boutons-modifier" type="button" onclick="updatePhoto()">Enregistrer la photo</button>
            </div>
            <div id="profile-info">
                <p>Nom d'utilisateur: <span id="username"></span></p>
                <p>Prénom: <span id="Prenom"></span></p>
                <p>Nom: <span id="nom"></span></p>
                <p>Email: <span id="email"></span></p>
                <p>Téléphone: <span id="phone"></span></p>
                <p>Adresse: <span id="adresse"></span></p>
                <button class="boutons-modifier" onclick="showEditProfile()">Modifier mes informations</button>
                <button class="boutons-modifier" onclick="showPhotoSelection()">Changer de photo</button>
            </div>
            <div id="edit-profile-form" style="display:none;">
                <h2>Modifier les informations</h2>
                <form id="profileForm">
                    <label for="edit-username">Nom d'utilisateur:</label>
                    <input type="text" id="edit-username" name="username">
                    <span id="username-error" style="color: red; display: none;">Ce nom d'utilisateur est déjà
                        pris.</span>

                    <label for="edit-prenom">Prénom:</label>
                    <input type="text" id="edit-prenom" name="prenom">

                    <label for="edit-nom">Nom:</label>
                    <input type="text" id="edit-nom" name="nom">

                    <label for="edit-email">Email:</label>
                    <input type="email" id="edit-email" name="email">

                    <label for="edit-phone">Téléphone:</label>
                    <input type="tel" id="edit-phone" name="phone">

                    <label for="edit-adresse">Adresse:</label>
                    <input type="text" id="edit-adresse" name="adresse">

                    <label for="edit-ville">Ville:</label>
                    <input type="text" id="edit-ville" name="ville">

                    <label for="edit-code-postal">Code postal:</label>
                    <input type="text" id="edit-code-postal" name="code_postal">

                    <label for="edit-pays">Pays:</label>
                    <input type="text" id="edit-pays" name="pays">

                    <label for="edit-password">Mot de passe:</label>
                    <input type="password" id="edit-password" name="password">

                    <label for="confirm-password">Confirmer le mot de passe:</label>
                    <input type="password" id="confirm-password" name="confirm-password">
                    <span id="password-error" style="color: red; display: none;">Les mots de passe ne correspondent
                        pas.</span>

                    <button type="button" onclick="updateProfile()">Enregistrer les modifications</button>
                </form>
            </div>
            <h2>Mes articles en vente</h2>
            <ul id="itemsList">
                <!-- Les articles ajoutés apparaîtront ici -->
            </ul>
        </div>
    </main>


    <footer>
        <p>&copy; 2024 eVente. Tous droits réservés.</p>
        <a href="conditions.php">Conditions d'utilisation</a> |
        <a href="confidentialite.php">Avis de confidentialité</a> |
        <a href="publicites.php">Publicités</a>
    </footer>

    <input type="hidden" id="selected-photo" value="pdp1.png"> <!-- Valeur par défaut -->
    <input type="hidden" id="user-id"> <!-- Assurez-vous d'avoir un ID utilisateur -->

    <script src="auth.js"></script>
    <script src="profil.js"></script>
    <script src="search.js"></script>
</body>

</html>