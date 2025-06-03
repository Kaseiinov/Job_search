window.addEventListener('load', function () {
    document.getElementById('search-input').addEventListener('input', filterList);

    function filterList(){
        const searchInput = document.getElementById('search-input');
        const filter = searchInput.value.toLowerCase();
        const listItems = document.querySelectorAll('.list-group-item');

        listItems.forEach((item) => {
            let name = item.querySelector('.vacancy-name').innerText;
            if(name.toLowerCase().includes(filter)) {
                item.style.display = '';
            }else{
                item.style.display = 'none';
            }

        })

    }



    document.querySelector('.burger-filter').addEventListener('click', function () {
        document.querySelector('.filter-card').classList.toggle('d-none');
    })


    document.getElementById("filter-form").addEventListener("submit", (e) => {
        e.preventDefault();
        const newFilter = {
            categoryId: document.getElementById("category").value,
            minPrice: document.getElementById("min-salary").value
        };
        updateFilter(newFilter);
    });


    document.getElementById("reset-filter").addEventListener("click", (e) => {
        clearFilter();

    })

    async function fetchItems() {
        try {
            let filter;
            if(localStorage.getItem('filter')) {
                filter = JSON.parse(localStorage.getItem("filter"))
            }else{
                filter = {categoryId: 0, minPrice: 0}
            }
            const params = new URLSearchParams(filter).toString();
            const response = await fetch(`/api/vacancies?${params}`);
            const data = await response.json();
            console.log(data)

            renderItems(data);
        } catch (error) {
            console.error("Ошибка:", error);
        }
    }

    function updateFilter(newFilter) {
        localStorage.setItem("filter", JSON.stringify(newFilter));
        fetchItems();
    }

    function clearFilter() {
        localStorage.removeItem("filter");
        window.location.href = "/";
    }



    function renderItems(items) {
        const vacancyList = document.querySelector('#vacancies-list')
        document.querySelector('.pagination').style.display = 'none'
        vacancyList.innerHTML = ' '

        items.forEach(item => {
            const card = document.createElement('div')
            card.className =  "card w-100 mb-3 overflow-hidden list-group-item"
            card.setAttribute('data-id', item.id)
            card.innerHTML = `
                <div class="card-body">
                <h5 class="card-title vacancy-name">${item.name}</h5>
                <h6 class="card-subtitle mb-2 text-muted">
                    ${item.expFrom ?? 0} - ${item.expTo ?? 0}
                </h6>
                <p class="card-text">${item.description ?? ''}</p>
                <p class="card-text">${item.salary ?? ''}</p>
            </div>
            `
            vacancyList.appendChild(card)

        })


    }

    if(localStorage.getItem('filter')) {
        fetchItems();
    }


})