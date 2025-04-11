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

</@main.layout>