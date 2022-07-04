let pagesRequiredBefore = 2;
let pagesRequiredAfter = 2;

function createPagination(totalPages, page) {
    let liTag = '';
    let active;
    let beforePage = Math.max(page - pagesRequiredBefore, 1);
    let afterPage = Math.min(page + pagesRequiredAfter, totalPages);

    // Prev button
    if (page > 1) {
        liTag += `<li class="btn prev" onclick="createPagination(totalPages, ${page - 1})"><span><i class="fas fa-angle-left"></i></span></li>`;
    }

    // Before dots
    if (page > pagesRequiredBefore + 1) {
        liTag += `<li class="first numb" onclick="createPagination(totalPages, 1)"><span>1</span></li>`;
        if (page > pagesRequiredBefore + 2) {
            liTag += `<li class="dots"><span>...</span></li>`;
        }
    }

    // Between dots
    for (var plength = beforePage; plength <= afterPage; plength++) {
        if (page == plength) {
            active = "active";
        } else {
            active = "";
        }
        liTag += `<li class="numb ${active}" onclick="createPagination(totalPages, ${plength})"><span>${plength}</span></li>`;
    }

    // After dots
    if (page < totalPages - pagesRequiredAfter) {
        if (page < totalPages - pagesRequiredAfter - 1) {
            liTag += `<li class="dots"><span>...</span></li>`;
        }
        liTag += `<li class="last numb" onclick="createPagination(totalPages, ${totalPages})"><span>${totalPages}</span></li>`;
    }

    // Next page
    if (page < totalPages) {
        liTag += `<li class="btn next" onclick="createPagination(totalPages, ${page + 1})"><span><i class="fas fa-angle-right"></i></span></li>`;
    }

    for (let i = 1; i <= totalPages; i++){
        document.getElementById("pagination-page-" + i).style.display = "none";
    }
    document.getElementById("pagination-page-" + page).style.display = "block";

    element.innerHTML = liTag;
    return liTag;
}