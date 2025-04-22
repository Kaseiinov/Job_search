<#import "../layout.ftlh" as main>
<@main.layout>

    <h2 class="mb-4 text-center">Список вакансий</h2>
        <#list vacancies as vacancy>

            <table class="table table-bordered table-striped">
                <thead class="table-dark">
                <tr>
                    <th>Название</th>
                    <th>Описание</th>
                    <th>Категория</th>
                    <th>Зарплата</th>
                    <th>Опыт (от - до)</th>
                    <th>Активно</th>
                    <th>Дата создания</th>
                    <th>Обновлено</th>
                </tr>
                </thead>
                <tbody>
                <tr >
                    <td >${vacancy.name}</td>
                    <td >${vacancy.description!}</td>
                    <td >${vacancy.categoryId}</td>
                    <td >${vacancy.salary!}</td>
                    <td >
                        <#if vacancy.expFrom?? || vacancy.expTo??>
                            ${vacancy.expFrom!0} - ${vacancy.expTo!0}
                        <#else>
                            Нет опыта
                        </#if>
                    </td>
                    <td>
                        <span>${vacancy.isActive?then('Да', 'Нет')}</span>
                    </td>
                    <td >${vacancy.createdDateFormatted}</td>
                    <td >${vacancy.updateTimeFormatted!}</td>
                </tr>
                </tbody>
            </table>
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