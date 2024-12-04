document.addEventListener('DOMContentLoaded', () => {
    const emailForm = document.getElementById('emailForm');
    const securityQuestionsForm = document.getElementById('securityQuestionsForm');
    const newPasswordForm = document.getElementById('newPasswordForm');
    const messageDiv = document.getElementById('message');

    if (emailForm) {
        emailForm.addEventListener('submit', async (event) => {
            event.preventDefault();
            const formData = new FormData(emailForm);
            const data = {
                email: formData.get('email')
            };

            try {
                const response = await fetch('/api/check_email', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(data)
                });

                if (!response.ok) {
                    const errorResponse = await response.json();
                    throw new Error(errorResponse.error || 'Erreur lors de la vérification de l\'email');
                }

                const responseData = await response.json();
                console.log('Réponse de vérification email :', responseData);
                displaySecurityQuestions(responseData.questions);
            } catch (error) {
                console.error('Erreur lors de la vérification de l\'email :', error.message);
                messageDiv.textContent = `Erreur: ${error.message}`;
            }
        });
    }

    if (securityQuestionsForm) {
        securityQuestionsForm.addEventListener('submit', async (event) => {
            event.preventDefault();
            const answers = Array.from(document.querySelectorAll('#questions input')).map(input => input.value.trim().toLowerCase());
            const data = { answers };

            try {
                const response = await fetch('/api/check_security_answers', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(data)
                });

                if (!response.ok) {
                    const errorResponse = await response.json();
                    throw new Error(errorResponse.error || 'Erreur lors de la vérification des réponses');
                }

                const responseData = await response.json();
                console.log('Réponse de vérification des réponses de sécurité :', responseData);
                document.getElementById('step2').style.display = 'none';
                document.getElementById('step3').style.display = 'block';
            } catch (error) {
                console.error('Erreur lors de la vérification des réponses :', error.message);
                messageDiv.textContent = `Erreur: ${error.message}`;
            }
        });
    }

    if (newPasswordForm) {
        newPasswordForm.addEventListener('submit', async (event) => {
            event.preventDefault();
            const formData = new FormData(newPasswordForm);
            const newPassword = formData.get('newPassword');
            const confirmPassword = formData.get('confirmPassword');

            if (newPassword !== confirmPassword) {
                messageDiv.textContent = 'Les mots de passe ne correspondent pas';
                return;
            }

            const data = { newPassword };

            try {
                const response = await fetch('/api/reset_password', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(data)
                });

                if (!response.ok) {
                    const errorResponse = await response.json();
                    throw new Error(errorResponse.error || 'Erreur lors de la réinitialisation du mot de passe');
                }

                alert('Mot de passe réinitialisé avec succès.');
                window.location.href = 'login.php'; // Redirection vers la page de connexion
            } catch (error) {
                console.error('Erreur lors de la réinitialisation du mot de passe :', error.message);
                messageDiv.textContent = `Erreur: ${error.message}`;
            }
        });
    }

    function displaySecurityQuestions(questions) {
        const questionsDiv = document.getElementById('questions');
        questionsDiv.innerHTML = '';
        questions.forEach((question, index) => {
            const questionDiv = document.createElement('div');
            questionDiv.innerHTML = `
                <label for="answer${index}">${question}</label>
                <input type="text" id="answer${index}" name="answer${index}" required>
            `;
            questionsDiv.appendChild(questionDiv);
        });
        document.getElementById('step1').style.display = 'none';
        document.getElementById('step2').style.display = 'block';
    }
});
