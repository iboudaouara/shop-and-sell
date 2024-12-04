document.addEventListener('DOMContentLoaded', () => {
    console.log('CompteVendeur.js loaded');

    function getVendorIdFromUrl() {
        const params = new URLSearchParams(window.location.search);
        return params.get('id');
    }

    const contentPage = document.querySelector('.main-container');
    const messageErreur = document.querySelector('.messageErreur');
    const sectionArticles = document.querySelector('.list-element');
    const sectionCommentaires = document.querySelector('.comments-container');
    const listeCommentaires = document.querySelector('#liste-commentaires');
    const listeArticles = document.querySelector('#liste-articles');
    const btnArticles = document.querySelector('.btn-articles');
    const btnCommentaires = document.querySelector('.btn-commentaires');
    const formCommentaire = document.querySelector('#commentForm');
    const inputCommentaire = document.querySelector('#commentText');
    const btnDeleteCom = document.querySelector('.delete-com');

    const vendorId = getVendorIdFromUrl();
    if (!vendorId) {
        console.error('Vendor ID not found in the URL');
        contentPage.classList.add('hidden');
        messageErreur.classList.remove('hidden');
        return;
    }

    let articlesData = [];

    function afficherArticles() {
        sectionCommentaires.classList.add('hidden');
        sectionArticles.classList.remove('hidden');
        afficherArticlesDansListe(articlesData);
    }

    function afficherCommentaires() {
        sectionArticles.classList.add('hidden');
        sectionCommentaires.classList.remove('hidden');
        chargerCommentaires();
    }

    function chargerCommentaires() {
        listeCommentaires.innerHTML = '';
        fetch(`/api/commentaires?vendeur_id=${vendorId}`)
            .then(response => response.json())
            .then(data => {
                if (data.length === 0) {
                    afficherCommentaireVide("Aucun avis disponible pour le moment.");
                } else {
                    data.forEach(commentaire => afficherCommentaire(commentaire));
                }
            })
            .catch(error => {
                console.error('Erreur lors de la récupération des commentaires:', error);
                afficherCommentaireVide("Erreur lors de la récupération des commentaires.");
            });
    }

    function afficherCommentaireVide(message) {
        const li = document.createElement('li');
        li.classList.add('comment');
        li.innerHTML = `
            <p class="comment-user">Rien à voir</p>
            <p class="comment-text">${message}</p>
        `;
        listeCommentaires.appendChild(li);
    }

    function afficherCommentaire(commentaire) {
        const li = document.createElement('li');
        li.classList.add('comment');
        li.innerHTML = `
            <p class="comment-user">${commentaire.username || 'Utilisateur inconnu'}</p>
            <p class="comment-text">${commentaire.commentaire}</p>
        `;
        listeCommentaires.appendChild(li);
    }

    function ajouterCommentaire(commentaire, utilisateurId) {
        fetch('/api/commentaires', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ vendeur_id: vendorId, utilisateur_id: utilisateurId, commentaire: commentaire })
        })
        .then(response => response.json())
        .then(data => {
            if (data.message === "Commentaire ajouté avec succès") {
                chargerCommentaires();
            } else {
                console.error('Erreur lors de l\'ajout du commentaire:', data.message || data.error);
            }
        })
        .catch(error => console.error('Erreur lors de l\'ajout du commentaire:', error));
    }

    function supprimerCommentaire() {
        const utilisateurId = localStorage.getItem('id');
        if (!utilisateurId) {
            alert('Vous devez être connecté pour supprimer vos commentaires');
            return;
        }

        if (confirm("Êtes-vous sûr de vouloir supprimer tous vos commentaires pour ce vendeur ?")) {
            fetch(`/api/commentaires?vendeur_id=${vendorId}&utilisateur_id=${utilisateurId}`, {
                method: 'DELETE'
            })
            .then(response => response.json())
            .then(data => {
                if (data.message === "Commentaires supprimés avec succès") {
                    alert("Vos commentaires ont bien été supprimés");
                    chargerCommentaires();
                } else {
                    alert("Une erreur s'est produite lors de la suppression de vos commentaires.");
                    console.error('Erreur:', data.message || data.error);
                }
            })
            .catch(error => console.error('Erreur lors de la suppression des commentaires:', error));
        }
    }

    function chargerDonneesVendeur() {
        fetch(`/api/utilisateurs/${vendorId}`)
            .then(response => response.json())
            .then(data => {
                if (data.error) {
                    console.error(data.error);
                    return;
                }
                const vendor = data.vendorOrUser || {};
                articlesData = data.articles || [];
                
                // Mise à jour de la photo de profil
                const profileImageElement = document.querySelector('.profile-image');
                if (vendor.photo_profil) {
                    profileImageElement.style.backgroundImage = `url('image/${vendor.photo_profil}')`;
                    profileImageElement.style.backgroundSize = 'cover';
                    profileImageElement.style.backgroundPosition = 'center';
                } else {
                    // Garder le style par défaut si pas de photo
                    profileImageElement.style.backgroundColor = '#ddd';
                }
                
                document.getElementById('profileImage').url_image = 'image/' + vendor.photo_profil;
                document.getElementById('sellerName').textContent = vendor.nom_utilisateur || 'Nom indisponible';
                document.getElementById('sellerEmail').textContent = vendor.email || 'Email indisponible';
                document.getElementById('sellerPhone').textContent = vendor.telephone ? `Numéro de téléphone: ${vendor.telephone}` : 'Numéro de téléphone indisponible';
                document.getElementById('sellerCountry').textContent = vendor.pays ? `Pays: ${vendor.pays}` : 'Pays indisponible';
                document.getElementById('articlesEnVente').textContent = articlesData.length;
                afficherArticlesDansListe(articlesData);
            })
            .catch(error => console.error('Error fetching vendor data:', error));
    }

    function afficherArticlesDansListe(articles) {
        listeArticles.innerHTML = '';
        articles.forEach(article => {
            const li = document.createElement('li');
            li.classList.add('element');
            li.innerHTML = `
                <a href="produits.php?id=${article.id}" class="article-link">
                    <div class="image-container">
                        <img src="${article.url_image || 'chemin/vers/image/par/defaut.jpg'}" alt="${article.nom}">
                    </div>
                    <div class="info-container">
                        <p class="article-nom">${article.nom}</p>
                        <p class="article-description">${article.description}</p>
                        <p class="article-prix">${article.prix} $</p>
                    </div>
                </a>
            `;
            listeArticles.appendChild(li);
        });
    }

    btnArticles.addEventListener('click', afficherArticles);
    btnCommentaires.addEventListener('click', afficherCommentaires);

    formCommentaire.addEventListener('submit', function(event) {
        event.preventDefault();
        const commentaire = inputCommentaire.value.trim();
        const utilisateurId = localStorage.getItem('id');
        if (commentaire && utilisateurId) {
            ajouterCommentaire(commentaire, utilisateurId);
            inputCommentaire.value = '';
        } else {
            alert('Vous devez être connecté pour entrer un commentaire.');
        }
    });

    if (btnDeleteCom) {
        btnDeleteCom.addEventListener('click', supprimerCommentaire);
    }

    chargerDonneesVendeur();
    afficherArticles();
});