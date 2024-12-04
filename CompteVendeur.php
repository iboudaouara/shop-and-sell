<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Site de Vente en Ligne</title>
    <link rel="stylesheet" href="CompteVendeur.css">
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
        <div class="messageErreur hidden">Erreur aucun vendeur trouvé</div>
        <div class="main-container">
            <div class="profile-section">
                <div class="profile-card">
                    <div class="profile-header">
                        <div class="profile-image" id="profileImage">
                            
                        </div>
                    </div>
                    <div class="profile-info">
                        <h2 id="sellerName"></h2>
                        <p id="sellerEmail"></p>
                        <p id="sellerPhone">Numéro de téléphone: </p>
                        <p id="sellerCountry">Pays: </p>
                        <div class="stats">
                            <div class="stat">
                                <p>Nombre d'articles en vente</p>
                                <p class="number" id="articlesEnVente"></p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="boutons-profil">
                    <button class="favorite styled btn-articles" type="button">Articles</button>
                    <button class="favorite styled btn-commentaires" type="button">Commentaires</button>
                </div>
            </div>

            <div>
                <nav class="list-element">
                    <h3>Articles à vendre :</h3>
                    <ul id="liste-articles">
                        <!-- Liste des articles -->
                    </ul>
                </nav>
    
                <div class="comments-container hidden">
                    <h3>Commentaires :</h3>
                    <ul id="liste-commentaires">
                        <!-- Liste des commentaires -->
                    </ul>
                    <div id="commentFormContainer">
                        <h4>Laisser un commentaire :</h4>
                        <form id="commentForm">
                            <textarea id="commentText" placeholder="Votre commentaire..."></textarea>
                            <button class="add-com" type="submit">Envoyer</button>
                        </form>
                        <button class="delete-com" type="delete">Supprimer mon commentaire</button>
                    </div>
                </div>
            </div>
        </div>
    </main>

    <footer>
        <p>&copy; 2024 eVente. Tous droits réservés.</p>
        <a href="conditions.php">Conditions d'utilisation</a> |
        <a href="confidentialite.php">Avis de confidentialité</a> |
        <a href="publicites.php">Publicités</a>
    </footer>

    <script src="auth.js"></script>
    <script src="CompteVendeur.js"></script>
    <script src="search.js"></script>
</body>
</html>
