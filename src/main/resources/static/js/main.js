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
        e.preventDefault();
        console.log(filter + " before")
        clearFilter();
        console.log(filter + " after")
    })

    async function fetchItems() {
        try {
            const filter = JSON.parse(localStorage.getItem("filter"));
            const params = new URLSearchParams(filter).toString();
            const response = await fetch(`/api/vacancies?${params}`);
            const items = await response.json();
            console.log(items.forEach(item => console.log(item.id)))
            renderItems(items); // Отрисовка данных
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
        fetchItems(); // Запрос без параметров
    }

    function renderItems(items) {
        const listItems = document.querySelectorAll('.list-group-item');
        console.log(listItems)

        listItems.forEach(item => {
            const vacancyId = item.getAttribute('data-id');
            items.forEach(vac => {
                if(vacancyId !== vac.id){
                    item.style.display = 'none';
                }else{
                    item.style.display = '';
                }
            }
            )
        });
    }

})