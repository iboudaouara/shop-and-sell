document.addEventListener('DOMContentLoaded', () => {
    updateUI();

    const form = document.getElementById('sellForm');
    if (form) {
        form.addEventListener('submit', (event) => {
            event.preventDefault();

            const utilisateurId = localStorage.getItem('id');
            const nom = document.getElementById('nom').value;
            const description = document.getElementById('description').value;
            const prix = document.getElementById('prix').value;
            const quantite = document.getElementById('quantite').value;
            const imageFile = document.getElementById('image').files[0];
            const type = document.getElementById('type').value;
            const nomUtilisateur = localStorage.getItem('username');

            if (utilisateurId) {
                const formData = new FormData();
                formData.append('utilisateur_id', utilisateurId);
                formData.append('nom', nom);
                formData.append('description', description);
                formData.append('prix', prix);
                formData.append('quantite', quantite);
                formData.append('image', imageFile);
                formData.append('type', type);
                formData.append('nom_utilisateur', nomUtilisateur);

                fetch('/api/articles', {
                    method: 'POST',
                    body: formData
                })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        alert("Article ajouté avec succès !");
                        form.reset();
                    } else {
                        // Affiche l'erreur détaillée renvoyée par le serveur
                        alert("Erreur lors de l'ajout de l'article : " + (data.error || data.message || 'Une erreur inconnue est survenue'));
                    }
                })
                .catch((error) => {
                    console.error('Error:', error);
                    alert("Une erreur est survenue : " + (error.message || "Veuillez réessayer."));
                });
            } else {
                alert("Vous devez être connecté pour ajouter un article.");
            }
        });
    }
});

function updateUI() {
    const isLoggedIn = localStorage.getItem('isLoggedIn') === 'true';
    const username = localStorage.getItem('username');
    const id = localStorage.getItem('id');
    const profileLink = document.getElementById('profileLink');
    const loginLink = document.getElementById('loginLink');
    const logoutButton = document.getElementById('logoutButton');
    const sellLink = document.getElementById('sellLink');

    if (isLoggedIn && username && id) {
        if (profileLink) profileLink.style.display = 'block';
        if (logoutButton) logoutButton.style.display = 'block';
        if (loginLink) loginLink.style.display = 'none';
        if (sellLink) sellLink.style.display = 'block';
    } else {
        if (profileLink) profileLink.style.display = 'none';
        if (logoutButton) logoutButton.style.display = 'none';
        if (loginLink) loginLink.style.display = 'block';
        if (sellLink) sellLink.style.display = 'none';
    }
}