document.addEventListener('DOMContentLoaded', () => {
    const params = new URLSearchParams(window.location.search);
    const productId = params.get('id');
    const loggedInUserId = localStorage.getItem('id');

    /*if (!loggedInUserId ) {
        console.error('User ID is not found in localStorage');
        showError('User not logged in.');
        return;
    }*/

    if (!productId || isNaN(productId) || productId <= 0) {
        console.error('Invalid product ID');
        showError('Invalid product ID.');
        return;
    }

    fetch(`/api/articles/${productId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(product => {
            if (!product || product.message) {
                showError('Product not found or API error.');
                return;
            }

            displayProduct(product);
            fetchSimilarProducts(product.type);
        })
        .catch(error => {
            console.error('Error fetching product data:', error);
            showError('Failed to fetch product data.');
        });
});

function displayProduct(product) {
    const container = document.getElementById('produitsContainer');
    if (!container) {
        console.error('Container element not found');
        return;
    }
    const card = createProductCard(product);
    container.appendChild(card);
}

function createProductCard(product, isSimilar = false) {
    const card = document.createElement('div');
    card.classList.add('card', product.type.toLowerCase());

    if (isSimilar) card.classList.add('similar-product-card');
    
    card.setAttribute('data-product-id', product.id);
    
    card.addEventListener('click', () => {
        window.location.href = `produits.php?id=${product.id}`;
    });

    const name = document.createElement('h1');
    name.textContent = product.nom;

    const image = document.createElement('img');
    image.setAttribute('alt', `Picture of the product ${product.nom}`);

    // Correction du chemin de l'image
    const imageUrl = product.url_image 
        ? `${product.url_image}`
        : '/chemin/vers/image/par/defaut.jpg';
    
    image.setAttribute('src', imageUrl);

    const prix = document.createElement('h2');
    prix.textContent = `${product.prix}$`;
    
    card.appendChild(name);
    card.appendChild(image);
    card.appendChild(prix);

    if (!isSimilar) {
        const infos = document.createElement('ul');
        infos.classList.add('description');

        infos.innerHTML = `
            <li>Type: ${product.type}</li>
            <li>Description: ${product.description}</li>
            <li>Quantité: ${product.quantite}</li>
            <li>Vendeur: <a href="CompteVendeur.php?id=${product.utilisateur_id}">${product.nom_utilisateur}</a></li>
            <li>Date d'ajout: ${new Date(product.date_ajout).toLocaleDateString()}</li>
        `;

        card.appendChild(infos);

        // Show delete button if the logged-in user is the owner of the product
        const loggedInUserId = localStorage.getItem('id');
        if (loggedInUserId && product.utilisateur_id === parseInt(loggedInUserId, 10)) {
            const deleteButton = document.createElement('button');
            deleteButton.textContent = 'Supprimer';
            deleteButton.classList.add('delete-button');
            
            deleteButton.addEventListener('click', (e) => {
                e.stopPropagation(); // Prevent the card click event
                showDeleteConfirmationModal(product.id);
            });

            card.appendChild(deleteButton);
        }
    } else {
        const vendeur = document.createElement('p');
        vendeur.classList.add('vendeur');
        vendeur.innerHTML = `Vendeur: <a href="CompteVendeur.php?id=${product.utilisateur_id}">${product.nom_utilisateur}</a>`;
        
        card.appendChild(vendeur);
    }

    return card;
}

function showDeleteConfirmationModal(productId) {
    const modal = document.createElement('div');
    modal.classList.add('modal');
    modal.innerHTML = `
        <div class="modal-content">
            <h2>Confirmation de suppression</h2>
            <p>Êtes-vous sûr de vouloir supprimer cet article ?</p>
            <div class="modal-buttons">
                <button id="confirmDelete">Confirmer</button>
                <button id="cancelDelete">Annuler</button>
            </div>
        </div>
    `;

    document.body.appendChild(modal);

    document.getElementById('confirmDelete').addEventListener('click', () => {
        deleteProduct(productId);
        closeModal();
    });

    document.getElementById('cancelDelete').addEventListener('click', closeModal);
}

function closeModal() {
    const modal = document.querySelector('.modal');
    if (modal) {
        modal.remove();
    }
}

function fetchSimilarProducts(productType) {
    fetch(`/api/articles/categories_limit/${productType}`)
        .then(response => response.json())
        .then(products => {
            if (Array.isArray(products)) {
                displaySimilarProducts(products);
            } else {
                console.error('Expected an array but got:', products);
                showError('Failed to fetch similar products.');
            }
        })
        .catch(error => {
            console.error('Error fetching similar products:', error);
            showError('Failed to fetch similar products.');
        });
}

function displaySimilarProducts(products) {
    const seeMoreContainer = document.getElementById('seeMore');
    if (!seeMoreContainer) {
        console.error('See More container element not found');
        return;
    }
    seeMoreContainer.innerHTML = '<h3>D\'autres produits qui pourraient vous intéresser</h3>';

    const productsContainer = document.createElement('div');
    productsContainer.classList.add('similar-products');

    products.forEach(product => {
        const card = createProductCard(product, true);
        productsContainer.appendChild(card);
    });

    seeMoreContainer.appendChild(productsContainer);
}

function deleteProduct(productId) {
    fetch(`/api/articles/${productId}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (!response.ok) {
            return response.json().then(error => {
                throw new Error(`Network response was not ok: ${error.message}`);
            });
        }
        if (response.status === 204) {
            const card = document.querySelector(`.card[data-product-id="${productId}"]`);
            if (card) {
                card.remove();
                console.log(`Product ${productId} deleted successfully.`);
            }
        } else {
            throw new Error(`Failed to delete product ${productId}. Status code: ${response.status}`);
        }
        window.location.href = 'articles.php';
    })
    .catch(error => {
        console.error('Error deleting product:', error);
        showError('Failed to delete the product.');
    });
}

function showError(message) {
    const container = document.getElementById('produitsContainer');
    if (!container) {
        console.error('Container element not found');
        return;
    }
    const errorMessage = document.createElement('p');
    errorMessage.textContent = message || 'An error occurred.';
    errorMessage.style.color = 'red'; 
    container.appendChild(errorMessage);
}