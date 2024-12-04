const apiUrl = '/api/login';

document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('loginForm');
    const logoutButton = document.getElementById('logoutButton');

    if (loginForm) {
        loginForm.addEventListener('submit', async (event) => {
            event.preventDefault();

            const formData = new FormData(loginForm);
            const data = {
                email: formData.get('email'),
                password: formData.get('password')
            };

            try {
                const response = await fetch(apiUrl, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(data)
                });

                if (!response.ok) {
                    const errorResponse = await response.json();
                    console.error('Erreur de l\'API :', errorResponse);
                    throw new Error(errorResponse.error || 'Erreur lors de la connexion');
                }

                const responseData = await response.json();
                console.log('Réponse de connexion :', responseData);
                alert('Vous êtes connecté avec succès.');

                // Stocker l'état de connexion et les informations de l'utilisateur dans localStorage
                localStorage.setItem('isLoggedIn', 'true');
                localStorage.setItem('username', responseData.user.username); // Stocke le nom de l'utilisateur
                localStorage.setItem('id', responseData.user.id); // Stocke l'ID de l'utilisateur
                console.log('Réponse de connexion :', responseData);
                
                // Mettre à jour l'interface utilisateur
                updateUI();

                // Rediriger l'utilisateur vers une autre page après la connexion
                window.location.href = 'accueil.php';
            } catch (error) {
                console.error('Erreur lors de la connexion :', error.message);
                alert('Une erreur est survenue lors de la connexion. Veuillez réessayer.');
            }
        });
    }

    if (logoutButton) {
        logoutButton.addEventListener('click', () => {
            // Déconnexion de l'utilisateur
            localStorage.removeItem('isLoggedIn');
            localStorage.removeItem('username'); // Supprime le nom de l'utilisateur
            localStorage.removeItem('id'); // Supprime l'ID de l'utilisateur

            // Mettre à jour l'interface utilisateur
            updateUI();

            // Rediriger l'utilisateur vers la page de connexion
            window.location.href = 'login.php';
        });
    }

    // Vérifier l'état de connexion et mettre à jour l'interface utilisateur au chargement de la page
    updateUI();
});

function updateUI() {
    const isLoggedIn = localStorage.getItem('isLoggedIn') === 'true';
    const username = localStorage.getItem('username');
    const id = localStorage.getItem('id');
    const profileLink = document.getElementById('profileLink');
    const loginLink = document.getElementById('loginLink');
    const logoutButton = document.getElementById('logoutButton');
    const welcomeMessage = document.getElementById('welcomeMessage');

    if (isLoggedIn && username && id) {
        if (welcomeMessage) welcomeMessage.textContent = `Bienvenue, ${username} (${id})!`;

        if (profileLink) profileLink.style.display = 'block';
        if (logoutButton) logoutButton.style.display = 'block';
        if (loginLink) loginLink.style.display = 'none';
    } else {
        if (welcomeMessage) welcomeMessage.textContent = 'Bienvenue!';

        if (profileLink) profileLink.style.display = 'none';
        if (logoutButton) logoutButton.style.display = 'none';
        if (loginLink) loginLink.style.display = 'block';
    }
}