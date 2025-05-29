<#import "layout.ftlh" as main>
<@main.layout ; spring>



    <style>
        .card {
            border-radius: 25px;
        }
    </style>

    <script>
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

            <#--function applyFilter() {-->
            <#--    const inputValue = document.getElementById("basic-addon1").value;-->
            <#--    localStorage.setItem("filter", inputValue);-->
            <#--    fetchVacancies(inputValue);-->
            <#--}-->

            <#--function fetchVacancies(keyword) {-->
            <#--    fetch(`/?sortBy=bySalary&minSalary=${keyword}`, {-->
            <#--        method: 'GET',-->
            <#--        headers: {-->
            <#--            'Content-Type': 'application/json'-->
            <#--        },-->
            <#--    })-->
            <#--        .then(response => response.json())-->
            <#--        .then(data => {-->
            <#--            console.log('Ответ от сервера:', data);-->
            <#--        })-->
            <#--        .catch(error => {-->
            <#--            console.error('Ошибка:', error);-->
            <#--        });-->

            }
        })

    </script>

    <h2 class="mb-4 text-center"><@spring.message "vacancy.list.listOfVacancy" /></h2>

    <div class="row align-items-center mb-3">
        <div class="col-6">
            <input type="text" class="form-control p-2  " placeholder="Search here..." id="search-input">
        </div>
        <div class="col-3">
            <i class="bi bi-sliders fs-2 burger-filter" style="margin-bottom: 5px"></i>
        </div>
        <div class="col-3"><form method="get" action="/" class="d-flex justify-content-end">
                <select name="sortBy" class="form-sele ct w-auto" onchange="this.form.submit()">
                    <option value=""><@spring.message "index.form.sortBy" /></option>
                    <option value="dateDesc" <#if sortBy == "dateDesc">selected</#if>><@spring.message "index.form.newestFirst" /></option>
                    <option value="dateAsc" <#if sortBy == "dateAsc">selected</#if>><@spring.message "index.form.oldestFirst" /></option>
                    <option value="vacancyResponseDesc" <#if sortBy == "vacancyResponseDesc">selected</#if>><@spring.message "index.form.responseDesc" /></option>
                    <option value="vacancyResponseAsc" <#if sortBy == "vacancyResponseAsc">selected</#if>><@spring.message "index.form.responseAsc" /></option>
                </select>
            </form>
        </div>
    </div>



    <div class="row">
        <div class="col-3 d-none filter-card">
            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon1">Уровень зарплаты от</span>
                <input type="number" class="form-control" placeholder="1000" aria-label="Username" >
            </div>
        </div>
        <div class="col-9">
            <#list vacancies as vacancy>

                <div class="card w-100 mb-3 overflow-hidden list-group-item">
                    <div class="card-body">
                        <h5 class="card-title vacancy-name">${vacancy.name}</h5>
                        <h6 class="card-subtitle mb-2 text-muted">
                            <#if vacancy.expFrom?? || vacancy.expTo??>
                                ${vacancy.expFrom!0} - ${vacancy.expTo!0}
                            <#else>
                                <@spring.message "index.list.noExperience" />
                            </#if>
                        </h6>
                        <p class="card-text">${vacancy.description!}</p>
                        <p class="card-text">${vacancy.salary!}</p>
                        <#--                <a href="/" class="btn btn-outline-info">Card link</a>-->
                        <#--                <a href="/" class="btn btn-outline-info">Another link</a>-->
                    </div>
                </div>

            </#list>
        </div>
    </div>



    <nav >
        <div class="d-flex justify-content-center">
            <ul class="pagination">
                <#if hasPrevious>
                    <li class="page-item ">
                        <a class="page-link" href="/?page=${currentPage - 1}" ><@spring.message "index.nav.previous" /></a>
                    </li>
                <#else>
                    <li class="page-item disabled">
                        <a class="page-link" href="/?page=${currentPage - 1}" ><@spring.message "index.nav.previous" /></a>
                    </li>
                </#if>
                <#list 0..(totalPages - 1) as page>
                    <li class="page-item <#if page == currentPage>active</#if>">
                        <a class="page-link" href="/?page=${page}">${page + 1}</a>
                    </li>
                </#list>
                <#if hasNext>
                    <li class="page-item">
                        <a class="page-link" href="/?page=${currentPage + 1}"><@spring.message "index.nav.next" /></a>
                    </li>
                <#else>
                    <li class="page-item disabled">
                        <a class="page-link" href="/?page=${currentPage + 1}"><@spring.message "index.nav.next" /></a>
                    </li>
                </#if>

            </ul>
        </div>
    </nav>

</@main.layout>