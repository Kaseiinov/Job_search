<#import "layout.ftlh" as main>
<@main.layout ; spring>

    <style>
        .card {
            border-radius: 25px;
        }
    </style>

    <h2 class="mb-4 text-center"><@spring.message "vacancy.list.listOfVacancy" /></h2>

    <form method="get" action="/" class="d-flex justify-content-end mb-3">
        <select name="sortBy" class="form-select w-auto" onchange="this.form.submit()">
            <option value=""><@spring.message "index.form.sortBy" /></option>
            <option value="dateDesc" <#if sortBy == "dateDesc">selected</#if>><@spring.message "index.form.newestFirst" /></option>
            <option value="dateAsc" <#if sortBy == "dateAsc">selected</#if>><@spring.message "index.form.oldestFirst" /></option>
        </select>
    </form>

    <#list vacancies as vacancy>

        <div class="card w-100 mb-3 overflow-hidden">
            <div class="card-body">
                <h5 class="card-title">${vacancy.name}</h5>
                <h6 class="card-subtitle mb-2 text-muted">
                    <#if vacancy.expFrom?? || vacancy.expTo??>
                        ${vacancy.expFrom!0} - ${vacancy.expTo!0}
                    <#else>
                        <@spring.message "index.list.noExperience" />
                    </#if>
                </h6>
                <p class="card-text">${vacancy.description!}</p>
<#--                <a href="/" class="btn btn-outline-info">Card link</a>-->
<#--                <a href="/" class="btn btn-outline-info">Another link</a>-->
            </div>
        </div>


    </#list>


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