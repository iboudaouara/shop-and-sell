<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Vendre un article - Shop&Sell</title>
    <link rel="stylesheet" href="sell.css">
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
        <div class="form-container">
            <h1>Vendre un article</h1>
            <form action="upload.php" method="post" enctype="multipart/form-data" id="sellForm">
                <label for="nom">Nom de l'article:</label>
                <input type="text" id="nom" name="nom" placeholder="Nom de l'article" required>

                <label for="description">Description de l'article:</label>
                <textarea id="description" name="description" placeholder="Description de l'article"></textarea>

                <label for="prix">Prix:</label>
                <input type="number" id="prix" name="prix" placeholder="Prix" step="0.01" required>

                <label for="quantite">Quantité:</label>
                <input type="number" id="quantite" name="quantite" placeholder="Quantité" required>

                <label for="image">Image de l'article:</label>
                <input type="file" id="image" name="image" accept="image/*" required>

                <label for="type">Type:</label> 
                <select id="type" name="type" required>
                    <option value="" disabled selected>Sélectionner un type</option>
                    <option value="Vetements">Vêtements</option>
                    <option value="Electronique">Électronique</option>
                    <option value="Sport">Sport</option>
                    <option value="Education">Éducation</option>
                    <option value="Accessoires">Accessoires</option>
                    <option value="Decorations">Décorations</option>
                    <option value="SanteBeaute">Santé et beauté</option>
                </select>

                <button type="submit">Ajouter l'article</button>
            </form>
        </div>
    </main>

    <footer>
        <p>&copy; 2024 Shop&Sell. Tous droits réservés.</p>
        <a href="conditions.php">Conditions d'utilisation</a> |
        <a href="confidentialite.php">Avis de confidentialité</a> |
        <a href="publicites.php">Publicités</a>
    </footer>

    <dialog id="confirm-exit-dialog">
        <form method="dialog">
            <p>Voulez-vous vraiment quitter cette page ? Les informations non sauvegardées seront perdues.</p>
            <menu>
                <button value="cancel">Annuler</button>
                <button value="confirm">Confirmer</button>
            </menu>
        </form>
    </dialog>

    <dialog id="error-dialog">
        <form method="dialog">
            <p id="error-message">Message d'erreur</p>
            <button value="OK">OK</button>
        </form>
    </dialog>

    <script src="sell.js"></script>
    <script src="search.js"></script>
    <script src="auth.js"></script>

</body>

</html>