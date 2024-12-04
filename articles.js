document.addEventListener('DOMContentLoaded', () => {
    console.log("DOM fully loaded and parsed");

    const urlParams = new URLSearchParams(window.location.search);
    const searchQuery = urlParams.get('search');

    const applySort = document.getElementById('applySort');
    if (applySort) {
        console.log("Apply sort button found");
        applySort.addEventListener('click', () => {
            console.log("Apply sort button clicked");
            fetchArticles();
        });
    } else {
        console.error("Apply sort button not found");
    }

    const resetSort = document.getElementById('resetSort');
    if (resetSort) {
        console.log("Reset sort button found");
        resetSort.addEventListener('click', () => {
            console.log("Reset sort button clicked");
            resetSortOptions();
        });
    } else {
        console.error("Reset sort button not found");
    }

    if (searchQuery) {
        fetch(`/api/searcharticle?search=${encodeURIComponent(searchQuery)}`)
            .then(response => response.json())
            .then(data => {
                if (data.length > 0) {
                    addArticles(data);
                } else {
                    const main = document.querySelector('.articles-list');
                    if (main) {
                        main.innerHTML = '<p>Aucun article trouvé.</p>';
                    } else {
                        console.error('Élément .articles-list non trouvé dans le DOM');
                    }
                }
            })
            .catch(error => {
                console.error('Error:', error);
            });
    } else {
        fetchArticles();
    }
});

function resetSortOptions() {
    // Réinitialiser les cases à cocher
    const checkboxes = document.querySelectorAll('#sortOptions input[type="checkbox"]');
    checkboxes.forEach(checkbox => {
        checkbox.checked = false;
    });

    // Réinitialiser les champs de prix
    document.getElementById('minPrice').value = '';
    document.getElementById('maxPrice').value = '';

    // Refetch des articles sans filtres
    fetchArticles();
}

function fetchArticles() {
    console.log("Fetching articles...");
    const urlParams = new URLSearchParams(window.location.search);
    let categoryType = urlParams.get('type');

    let url = '/api/articles';
    if (categoryType && categoryType !== 'All') {
        url = `/api/articles/categories/${categoryType}`;
    }

    fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        console.log("Fetched Articles:", data);
        if (Array.isArray(data) && data.length > 0) {
            sortArticles(data);
        } else {
            console.log("No articles found");
            displayNoArticlesMessage();
        }
    })
    .catch(error => {
        console.error('Error fetching articles data:', error);
        displayNoArticlesMessage();
    });
}

function displayNoArticlesMessage() {
    const main = document.querySelector('.articles-list');
    if (main) {
        main.innerHTML = '<p>Aucun article trouvé.</p>';
    } else {
        console.error('Élément .articles-list non trouvé dans le DOM');
    }
}

function sortArticles(articlesList) {
    console.log("Sorting articles...");

    // Filtrage par intervalle de prix
    const minPrice = parseFloat(document.getElementById('minPrice').value) || 0;
    const maxPrice = parseFloat(document.getElementById('maxPrice').value) || Infinity;
        
    articlesList = articlesList.filter(article => {
        const price = parseFloat(article.prix);
        return price >= minPrice && price <= maxPrice;
    });

    // Tri
    const sortOptions = document.querySelectorAll('#sortOptions input[type="checkbox"]:checked');
    console.log("Selected sort options:", Array.from(sortOptions).map(o => o.value));
    
    articlesList.sort((a, b) => {
        let nameOrder = 0;
        let priceOrder = 0;

        sortOptions.forEach(option => {
            switch(option.value) {
                case 'nameAsc':
                    nameOrder = a.nom.localeCompare(b.nom);
                    console.log(`Comparing names (Asc): ${a.nom} vs ${b.nom} = ${nameOrder}`);
                    break;
                case 'nameDesc':
                    nameOrder = b.nom.localeCompare(a.nom);
                    console.log(`Comparing names (Desc): ${a.nom} vs ${b.nom} = ${nameOrder}`);
                    break;
                case 'priceAsc':
                    priceOrder = parseFloat(a.prix) - parseFloat(b.prix);
                    console.log(`Comparing prices (Asc): ${a.prix} vs ${b.prix} = ${priceOrder}`);
                    break;
                case 'priceDesc':
                    priceOrder = parseFloat(b.prix) - parseFloat(a.prix);
                    console.log(`Comparing prices (Desc): ${a.prix} vs ${b.prix} = ${priceOrder}`);
                    break;
            }
        });

        // Appliquer d'abord le tri par nom, puis par prix si les noms sont égaux
        console.log(`Final comparison result: ${nameOrder || priceOrder}`);
        return nameOrder || priceOrder;
    });

    console.log("Articles sorted:", articlesList.map(a => `${a.nom} - ${a.prix}`));
    addArticles(articlesList);
}

function addArticles(articlesList) {
    console.log("Adding articles:", articlesList);

    const main = document.querySelector('.articles-list');

    console.log("Main element:", main);

    if (!main) {
        console.error("Élément .articles-list non trouvé dans le DOM");
        return;
    }

    main.innerHTML = '';

    if (!articlesList.length || articlesList.length === 0) {
        const noArticlesMessage = document.createElement('p');
        noArticlesMessage.textContent = 'Aucun article trouvé.';
        main.appendChild(noArticlesMessage);
        return;
    }

    articlesList.forEach(article => {
        console.log('Adding article:', article);

        const square = document.createElement('div');
        square.classList.add('articles-card');

        square.addEventListener('click', () => {
            window.location.href = `produits.php?id=${article.id}`;
        });

        const img = document.createElement('img');
        img.setAttribute('src', article.url_image || 'chemin/vers/image/par/defaut.jpg');
        img.setAttribute('alt', article.nom);

        const details = document.createElement('div');
        details.classList.add('details');

        const nameLink = document.createElement('a');
        nameLink.setAttribute('href', `produits.php?id=${article.id}`);
        nameLink.setAttribute('alt', `Name of the article ${article.nom}`);
        nameLink.textContent = article.nom;

        const types = document.createElement('ul');
        types.classList.add('description');
        types.innerHTML = `
            <li>Description: ${article.description}</li>
            <li>Prix: ${article.prix}$</li>
            <li>Quantité: ${article.quantite}</li>
            <li>Vendeur: <a href="CompteVendeur.php?id=${article.utilisateur_id}">${article.nom_utilisateur}</a></li>
        `;

        square.appendChild(img);
        details.appendChild(nameLink);
        details.appendChild(types);
        square.appendChild(details);
        main.appendChild(square);
    });

    console.log("Articles added to DOM");
}