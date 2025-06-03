<#import "layout.ftlh" as main>
<@main.layout ; spring>

    <style>
        .card {
            border-radius: 25px;
        }
    </style>

    <script src="../static/js/main.js"></script>

    <h2 class="mb-4 text-center"><@spring.message "vacancy.list.listOfVacancy" /></h2>

    <div class="row align-items-center mb-3">
        <div class="col-6">
            <input type="text" class="form-control p-2  " placeholder="Search here..." id="search-input">
        </div>
        <div class="col-3">
            <i class="bi bi-sliders fs-2 burger-filter btn" style="margin-bottom: 5px"></i>
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
            <form id="filter-form">

                <select id="category" class="mb-3 form-select">
                    <option value="0">Любая категория</option>
                    <#list categories as category>
                        <option value="${category.id}">${category.name}</option>
                    </#list>


                </select>

                <input min="0" class="mb-3 form-control" type="number" id="min-salary" placeholder="Мин. зарплата">

                <div>
                    <button class="mb-3 btn btn-outline-info" type="submit">Применить фильтр</button>
                </div>
            </form>
            <button class="btn btn-outline-warning" type="button" id="reset-filter">Сбросить</button>


        </div>
        <div class="col-9">
            <div id="vacancies-list">
                <#list vacancies as vacancy>

                    <div class="card w-100 mb-3 overflow-hidden list-group-item" data-id="${vacancy.id}">
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
    </div>



    <nav >
        <div class="d-flex justify-content-center navigation">
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