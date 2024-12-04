document.addEventListener('DOMContentLoaded', () => {
    console.log('DOM chargé, appel de updateUI');

    // Appeler updateUI pour initialiser l'état de la page
    updateUI();

    const logoutButton = document.getElementById('logoutButton');
    if (logoutButton) {
        logoutButton.addEventListener('click', () => {
            console.log('Bouton de déconnexion cliqué');
            // Déconnexion de l'utilisateur
            localStorage.removeItem('isLoggedIn');
            localStorage.removeItem('username');
            localStorage.removeItem('id');

            console.log('Données de session supprimées, appel de updateUI');
            updateUI();

            // Rediriger l'utilisateur vers la page de connexion
            window.location.href = 'login.php';
        });
    } else {
        console.log('Bouton de déconnexion non trouvé');
    }

    // Vérifier l'état de connexion et mettre à jour l'interface utilisateur au chargement de la page
    checkLoginStatus();
});

function updateUI() {
    console.log('Fonction updateUI appelée');
    const isLoggedIn = localStorage.getItem('isLoggedIn') === 'true';
    const username = localStorage.getItem('username');
    const id = localStorage.getItem('id');
    console.log('État de connexion:', isLoggedIn);
    console.log('Nom d\'utilisateur stocké:', username);

    const profileLink = document.getElementById('profileLink');
    const loginLink = document.getElementById('loginLink');
    const logoutButton = document.getElementById('logoutButton');
    const sellLink = document.getElementById('sellLink'); // Lien "Vendre"

    if (isLoggedIn && username && id) {
        console.log('Utilisateur connecté');
        if (profileLink) profileLink.style.display = 'block';
        if (logoutButton) logoutButton.style.display = 'block';
        if (loginLink) loginLink.style.display = 'none';
        if (sellLink) sellLink.style.display = 'block'; // Afficher le lien "Vendre"
    } else {
        console.log('Utilisateur non connecté ou nom d\'utilisateur manquant');
        if (profileLink) profileLink.style.display = 'none';
        if (logoutButton) logoutButton.style.display = 'none';
        if (loginLink) loginLink.style.display = 'block';
        if (sellLink) sellLink.style.display = 'none'; // Masquer le lien "Vendre"
    }

    console.log('Mise à jour de l\'interface terminée');
}

// Fonction pour vérifier et mettre à jour le statut de connexion
function checkLoginStatus() {
    console.log('Vérification du statut de connexion');
    const isLoggedIn = localStorage.getItem('isLoggedIn') === 'true';
    const username = localStorage.getItem('username');
    const id = localStorage.getItem('id');

    // Vérifier la cohérence des données
    if (isLoggedIn && (!username || !id)) {
        console.log('Incohérence détectée : connecté mais sans nom d\'utilisateur ou ID');
        // Gérer l'incohérence (par exemple, déconnecter l'utilisateur)
        localStorage.removeItem('isLoggedIn');
        localStorage.removeItem('username');
        localStorage.removeItem('id');
        updateUI();
    }
}
