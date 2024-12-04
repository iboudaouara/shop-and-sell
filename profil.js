// Fonction pour afficher le formulaire de modification du profil
function showEditProfile() {
    const form = document.getElementById('edit-profile-form');
    if (form) {
        form.style.display = 'block';
    } else {
        console.error('Élément "edit-profile-form" non trouvé.');
    }
}

// Fonction pour afficher les options de sélection de photo
function showPhotoSelection() {
    const photoSelection = document.getElementById('photo-selection');
    if (photoSelection) {
        photoSelection.style.display = 'block';
    } else {
        console.error('Élément "photo-selection" non trouvé.');
    }
}

// Fonction pour sélectionner une photo de profil
function selectPhoto(photo) {
    const currentPhoto = document.getElementById('current-photo');
    const selectedPhotoInput = document.getElementById('selected-photo');
    
    if (currentPhoto && selectedPhotoInput) {
        currentPhoto.src = 'image/' + photo;
        selectedPhotoInput.value = photo;
    } else {
        console.error('Élément "current-photo" ou "selected-photo" non trouvé.');
    }
}

// Fonction pour mettre à jour les informations de profil
async function updateProfile() {
    const userId = getUserIdFromSession();
    if (!userId) {
        console.error('ID utilisateur non trouvé dans la session.');
        return;
    }

    const username = document.getElementById('edit-username').value;
    const email = document.getElementById('edit-email').value;
    const phone = document.getElementById('edit-phone').value;
    const password = document.getElementById('edit-password').value;
    const confirmPassword = document.getElementById('confirm-password').value;
    const prenom = document.getElementById('edit-prenom').value;
    const nom = document.getElementById('edit-nom').value;
    const adresse = document.getElementById('edit-adresse').value;
    const ville = document.getElementById('edit-ville').value;
    const codePostal = document.getElementById('edit-code-postal').value;
    const pays = document.getElementById('edit-pays').value;

    const passwordError = document.getElementById('password-error');
    if (password !== confirmPassword) {
        if (passwordError) {
            passwordError.style.display = 'block';
        }
        return;
    } else {
        if (passwordError) {
            passwordError.style.display = 'none';
        }
    }

    try {
        const response = await fetch('/api/utilisateurs/update', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ 
                id: userId, 
                username, 
                email, 
                phone, 
                password,
                prenom,
                nom,
                adresse,
                ville,
                code_postal: codePostal,
                pays
            })
        });

        const result = await response.json();

        if (result.success) {
            alert("Profil mis à jour !");
            const form = document.getElementById('edit-profile-form');
            if (form) {
                form.style.display = 'none';
            }
            loadProfile();
        } else {
            if (result.message === 'Nom d\'utilisateur déjà pris') {
                const usernameError = document.getElementById('username-error');
                if (usernameError) {
                    usernameError.style.display = 'block';
                }
            } else {
                alert("Erreur lors de la mise à jour du profil : " + result.message);
            }
        }
    } catch (error) {
        console.error('Erreur lors de la mise à jour du profil:', error);
    }
}

// Fonction pour mettre à jour la photo de profil
async function updatePhoto() {
    const userId = getUserIdFromSession();
    if (!userId) {
        console.error('ID utilisateur non trouvé dans la session.');
        return;
    }

    const selectedPhoto = document.getElementById('selected-photo').value;

    try {
        const response = await fetch('/api/utilisateurs/photo', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ id: userId, photo_profil: selectedPhoto })
        });

        const result = await response.json();

        if (result.success) {
            alert("Photo de profil mise à jour !");
            const photoSelection = document.getElementById('photo-selection');
            if (photoSelection) {
                photoSelection.style.display = 'none';
            }
            loadProfile();
        } else {
            alert("Erreur lors de la mise à jour de la photo de profil : " + result.message);
        }
    } catch (error) {
        console.error('Erreur lors de la mise à jour de la photo de profil:', error);
    }
}

// Fonction pour afficher les informations du profil
async function loadProfile() {
    try {
        const userId = getUserIdFromSession();
        if (!userId) {
            console.error('ID utilisateur non trouvé dans la session.');
            return;
        }

        const response = await fetch(`/api/utilisateurs/${userId}`);

        if (!response.ok) {
            throw new Error(`Erreur HTTP: ${response.status}`);
        }

        const utilisateur = await response.json();

        if (!utilisateur) {
            console.error("Aucun utilisateur trouvé.");
            return;
        }

        const usernameElem = document.getElementById('username');
        const prenomElem = document.getElementById('Prenom');
        const nomElem = document.getElementById('nom');
        const emailElem = document.getElementById('email');
        const phoneElem = document.getElementById('phone');
        const adresseElem = document.getElementById('adresse');
        const currentPhotoElem = document.getElementById('current-photo');
        const selectedPhotoInput = document.getElementById('selected-photo');

        if (usernameElem) usernameElem.textContent = utilisateur.vendorOrUser.nom_utilisateur || 'Nom d\'utilisateur indisponible';
        if (prenomElem) prenomElem.textContent = utilisateur.vendorOrUser.prenom || 'Prénom indisponible';
        if (nomElem) nomElem.textContent = utilisateur.vendorOrUser.nom || 'Nom indisponible';
        if (emailElem) emailElem.textContent = utilisateur.vendorOrUser.email || 'Email indisponible';
        if (phoneElem) phoneElem.textContent = utilisateur.vendorOrUser.telephone || 'Téléphone indisponible';
        if (adresseElem) {
            const adresseComplete = [
                utilisateur.vendorOrUser.adresse,
                utilisateur.vendorOrUser.ville,
                utilisateur.vendorOrUser.code_postal,
                utilisateur.vendorOrUser.pays
            ].filter(Boolean).join(', ');
            adresseElem.textContent = adresseComplete || 'Adresse indisponible';
        }
        if (currentPhotoElem) {
            currentPhotoElem.src = 'image/' + (utilisateur.vendorOrUser.photo_profil || 'pdp1.png');
        }
        if (selectedPhotoInput) {
            selectedPhotoInput.value = utilisateur.vendorOrUser.photo_profil || 'pdp1.png';
        }

        // Pré-remplir le formulaire de modification
        document.getElementById('edit-username').value = utilisateur.vendorOrUser.nom_utilisateur || '';
        document.getElementById('edit-email').value = utilisateur.vendorOrUser.email || '';
        document.getElementById('edit-phone').value = utilisateur.vendorOrUser.telephone || '';
        document.getElementById('edit-prenom').value = utilisateur.vendorOrUser.prenom || '';
        document.getElementById('edit-nom').value = utilisateur.vendorOrUser.nom || '';
        document.getElementById('edit-adresse').value = utilisateur.vendorOrUser.adresse || '';
        document.getElementById('edit-ville').value = utilisateur.vendorOrUser.ville || '';
        document.getElementById('edit-code-postal').value = utilisateur.vendorOrUser.code_postal || '';
        document.getElementById('edit-pays').value = utilisateur.vendorOrUser.pays || '';

    } catch (error) {
        console.error('Erreur lors du chargement du profil:', error);
    }
}

// Fonction pour obtenir l'ID utilisateur depuis la session
function getUserIdFromSession() {
    const utilisateurId = localStorage.getItem('id');
    return utilisateurId;
}

// Nouvelle fonction pour charger et afficher les articles de l'utilisateur
async function loadUserArticles() {
    const userId = getUserIdFromSession();
    if (!userId) {
        console.error('ID utilisateur non trouvé dans la session.');
        return;
    }

    try {
        const response = await fetch(`/api/utilisateurs/${userId}`);
        if (!response.ok) {
            throw new Error(`Erreur HTTP: ${response.status}`);
        }

        const data = await response.json();
        const articles = data.articles;

        const itemsList = document.getElementById('itemsList');
        if (!itemsList) {
            console.error('Élément "itemsList" non trouvé.');
            return;
        }

        itemsList.innerHTML = ''; // Vider la liste existante

        if (articles && articles.length > 0) {
            articles.forEach(article => {
                const li = document.createElement('li');
                li.className = 'article-item';
                li.innerHTML = `
                    <img src="${article.url_image}" alt="${article.nom}" class="article-image">
                    <div class="article-details">
                        <h3>${article.nom}</h3>
                        <p>Prix: ${article.prix} €</p>
                        <p>Quantité: ${article.quantite}</p>
                        <button onclick="deleteArticle(${article.id})">Supprimer</button>
                    </div>
                `;
                itemsList.appendChild(li);
            });
        } else {
            itemsList.innerHTML = '<p>Aucun article en vente.</p>';
        }
    } catch (error) {
        console.error('Erreur lors du chargement des articles:', error);
    }
}

// Fonction pour supprimer un article
async function deleteArticle(articleId) {
    if (confirm('Êtes-vous sûr de vouloir supprimer cet article ?')) {
        try {
            const response = await fetch(`/api/articles/${articleId}`, {
                method: 'DELETE'
            });

            if (response.ok) {
                alert('Article supprimé avec succès');
                loadUserArticles(); // Recharger la liste des articles
            } else {
                throw new Error("Erreur lors de la suppression de l'article");
            }
        } catch (error) {
            console.error('Erreur:', error);
            alert("Une erreur est survenue lors de la suppression de l'article");
        }
    }
}

// Initialisation
document.addEventListener('DOMContentLoaded', () => {
    loadProfile();
    loadUserArticles();
});