<#import "../layout.ftlh" as main>
<@main.layout>

        <h2 class="mb-4 text-center">Список резюме</h2>

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

</@main.layout>