const apiUrl = '/api/register';

document.addEventListener('DOMContentLoaded', () => {
    const creationCompteForm = document.querySelector('form');

    if (creationCompteForm) {
        creationCompteForm.addEventListener('submit', async (event) => {
            event.preventDefault();

            const formData = new FormData(creationCompteForm);
            const telephone = formData.get('telephone');
            const email = formData.get('email');
            
            const phonePattern = /^\d+$/;
            const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

            // Vérification du numéro de téléphone
            if (!phonePattern.test(telephone)) {
                alert("Le numéro de téléphone doit contenir uniquement des chiffres.");
                return;
            }

            // Vérification de l'email
            if (!emailPattern.test(email)) {
                alert("Veuillez entrer une adresse email valide.");
                return;
            }

            const data = {
                nom_utilisateur: formData.get('username'),
                email: formData.get('email'),
                password: formData.get('password'),
                prenom: formData.get('prenom'),
                nom: formData.get('nom'),
                telephone: formData.get('telephone'),
                question_securite1: formData.get('question_securite1'),
                reponse_securite1: formData.get('reponse_securite1'),
                question_securite2: formData.get('question_securite2'),
                reponse_securite2: formData.get('reponse_securite2')
            };

            // Vérification si les mots de passe correspondent
            const password = formData.get('password');
            const confirm_password = formData.get('confirm_password');
            if (password !== confirm_password) {
                alert("Les mots de passe ne correspondent pas.");
                return;
            }

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
                    throw new Error(errorResponse.error || 'Erreur lors de la création du compte');
                }

                alert('Le compte a été créé avec succès.');
                window.location.href = 'login.php';
                creationCompteForm.reset(); // Réinitialise le formulaire après succès

            } catch (error) {
                console.error('Erreur lors de la création de compte :', error.message);
                alert('Une erreur est survenue lors de la création du compte. Veuillez réessayer.');
            }
        });
    } else {
        console.error('Formulaire de création de compte introuvable');
    }
});
