<#import "../layout.ftlh" as main>
<@main.layout>

    <h2 class="mb-4 text-center">Список вакансий</h2>

    <form method="get" action="/vacancies" class="d-flex justify-content-end mb-3">
        <select name="sortBy" class="form-select w-auto" onchange="this.form.submit()">
            <option value="">Сортировать по</option>
            <option value="dateDesc" <#if sortBy == "dateDesc">selected</#if>>Сначала новые</option>
            <option value="dateAsc" <#if sortBy == "dateAsc">selected</#if>>Сначала старые</option>
        </select>
    </form>

    <#list vacancies as vacancy>

        <style>
            .card {
                border-radius: 25px;
            }
        </style>

        <div class="card w-100 mb-3 overflow-hidden">
            <div class="card-body">
                <h5 class="card-title">${vacancy.name}</h5>
                <h6 class="card-subtitle mb-2 text-muted">
                    <#if vacancy.expFrom?? || vacancy.expTo??>
                        ${vacancy.expFrom!0} - ${vacancy.expTo!0}
                    <#else>
                        Без опыта
                    </#if>
                </h6>
                <p class="card-text">${vacancy.description!}</p>
                <a href="/vacancies" class="btn btn-outline-info">Card link</a>
                <a href="/vacancies" class="btn btn-outline-info">Another link</a>
            </div>
        </div>


    </#list>


    <nav >
        <div class="d-flex justify-content-center">
            <ul class="pagination">
                <#if hasPrevious>
                    <li class="page-item ">
                        <a class="page-link" href="/vacancies?page=${currentPage - 1}" >Previous</a>
                    </li>
                <#else>
                    <li class="page-item disabled">
                        <a class="page-link" href="/vacancies?page=${currentPage - 1}" >Previous</a>
                    </li>
                </#if>
                <#list 0..(totalPages - 1) as page>
                    <li class="page-item <#if page == currentPage>active</#if>">
                        <a class="page-link" href="/vacancies?page=${page}">${page + 1}</a>
                    </li>
                </#list>
                <#if hasNext>
                    <li class="page-item">
                        <a class="page-link" href="/vacancies?page=${currentPage + 1}">Next</a>
                    </li>
                <#else>
                    <li class="page-item disabled">
                        <a class="page-link" href="/vacancies?page=${currentPage + 1}">Next</a>
                    </li>
                </#if>

            </ul>
        </div>
    </nav>

</@main.layout>