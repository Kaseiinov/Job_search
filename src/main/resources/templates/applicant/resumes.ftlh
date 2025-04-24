<#import "../layout.ftlh" as main>
<@main.layout>

        <h2 class="mb-4 text-center">Список резюме</h2>

    <form method="get" action="/resumes" class="w-50 mx-auto mb-3">
        <select name="sortBy" class="form-select" onchange="this.form.submit()">
            <option value="">Сортировать по</option>
            <option value="dateDesc" <#if sortBy == "dateDesc">selected</#if>>Сначала новые</option>
            <option value="dateAsc" <#if sortBy == "dateAsc">selected</#if>>Сначала старые</option>
        </select>
    </form>

        <#list resumes as resume>
        <table class="table table-bordered table-striped">
            <thead class="table-dark">
            <tr>
                <th>Соискатель</th>
                <th>Название</th>
                <th>Категория</th>
                <th>Зарплата</th>
                <th>Активно</th>
                <th>Дата создания</th>
                <th>Обновлено</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>${resume.applicantId}</td>
                <td>${resume.name}</td>
                <td>${resume.categoryId}</td>
                <td>${resume.salary}</td>
                <td>
                    <span>${resume.isActive?then('Да', 'Нет')}</span>
                </td>
                <td>${resume.createdDate}</td>
                <td>${resume.updateTime!}</td>
            </tr>
            </tbody>
        </table>
        </#list>

    <nav >
        <div class="d-flex justify-content-center">
            <ul class="pagination">
                <#if hasPrevious>
                    <li class="page-item ">
                        <a class="page-link" href="/resumes?page=${currentPage - 1}" >Previous</a>
                    </li>
                <#else>
                    <li class="page-item disabled">
                        <a class="page-link" href="/resumes?page=${currentPage - 1}" >Previous</a>
                    </li>
                </#if>
                <#list 0..(totalPages - 1) as page>
                    <li class="page-item <#if page == currentPage>active</#if>">
                        <a class="page-link" href="/resumes?page=${page}">${page + 1}</a>
                    </li>
                </#list>
                <#if hasNext>
                    <li class="page-item">
                        <a class="page-link" href="/resumes?page=${currentPage + 1}">Next</a>
                    </li>
                <#else>
                    <li class="page-item disabled">
                        <a class="page-link" href="/resumes?page=${currentPage + 1}">Next</a>
                    </li>
                </#if>

            </ul>
        </div>
    </nav>

</@main.layout>