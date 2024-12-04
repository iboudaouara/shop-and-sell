// Fonction pour supprimer les accents
function removeAccents(str) {
    const accents = 'ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÑÒÓÔÕÖØÙÚÛÜÝÞàáâãäåæçèéêëìíîïñòóôõöøùúûüýþÿ';
    const noAccents = 'AAAAAAACEEEEIIIINOOOOOOUUUUYTaaaaaaaceeeeiiiinoooooouuuuyty';

    return str.split('').map((char, index) => {
        const accentIndex = accents.indexOf(char);
        return accentIndex !== -1 ? noAccents[accentIndex] : char;
    }).join('');
}

document.getElementById('searchButton').addEventListener('click', function() {
    var searchQuery = document.getElementById('searchInput').value;
    if (searchQuery) {
        // Normaliser la requête de recherche avant de l'envoyer
        var normalizedSearchQuery = removeAccents(searchQuery);
        window.location.href = 'articles.php?search=' + encodeURIComponent(normalizedSearchQuery);
    }
});
