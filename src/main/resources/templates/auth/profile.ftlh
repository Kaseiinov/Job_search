<#import "../layout.ftlh" as main>
<@main.layout>

    <body class="bg-light">

        <div class="text-center mb-4">
            <h2 style="color: red;">
                Личный кабинет
                <#if user.accountType == "EMPLOYER">
                    Работодателя
                <#elseif user.accountType == "APPLICANT">
                    Соискателя
                </#if>
            </h2>
        </div>

        <div class="d-flex align-items-center justify-content-between mb-4">
            <div class="d-flex align-items-center">
                <img src="/images/${user.id}" width="80" height="80" alt="avatar" class="me-3">
                <div>
                    <#if user.accountType == "EMPLOYER">
                        <h5>${user.name!}</h5>
                    <#elseif user.accountType == "APPLICANT">
                        <h5>${user.name}</h5>
                        <p>Возраст: ${user.age}</p>
                    </#if>
                    <a href="/users/edit/${user.email}" class="btn btn-outline-success btn-sm mt-2">Редактировать</a>
                </div>
            </div>

            <div>
                <#if user.accountType == "APPLICANT">
                    <a href="/resumes/create" class="btn btn-outline-danger">+ Создать резюме</a>
                <#elseif user.accountType == "EMPLOYER">
                    <a href="/vacancies/create" class="btn btn-outline-danger">+ Создать вакансию</a>
                </#if>
                <#if user.accountType == "APPLICANT">
                    <a href="#" class="btn btn-outline-secondary position-relative ms-3">
                        Отклики
<#--                        <#if responsesCount?? && responsesCount > 0>-->
<#--                            <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">-->
<#--                            ${responsesCount}-->
<#--                        </span>-->
<#--                        </#if>-->
                    </a>
                </#if>
            </div>
        </div>

        <div>
            <h5>
                <#if user.accountType == "EMPLOYER">Созданные вакансии:<#else>Резюме:</#if>
            </h5>

            <div class="row">
                <#list items as item>
                    <div class="col-md-4">
                        <div class="card mb-3 shadow-sm">
                            <div class="card-body">
                                <h6 class="card-title">${item.name}</h6>
                                <p class="card-text text-muted">
                                    Дата обновления: ${item.updateTimeFormatted!""}
                                </p>
                                <#if user.accountType == "APPLICANT">
                                    <div class="d-flex justify-content-between">
                                        <a href="/resumes/update/${item.id}" class="btn btn-outline-primary btn-sm">Обновить</a>
                                        <a href="/resumes/edit/${item.id}" class="btn btn-outline-success btn-sm">Ред.</a>
                                    </div>
                                <#elseif user.accountType == "EMPLOYER">
                                    <div class="d-flex justify-content-between">
                                        <a href="/vacancies/update/${item.id}" class="btn btn-outline-primary btn-sm">Обновить</a>
                                        <a href="/vacancies/edit/${item.id}" class="btn btn-outline-success btn-sm">Ред.</a>
                                    </div>
                                </#if>

                            </div>
                        </div>
                    </div>
                </#list>
            </div>
        </div>

    </body>

    </@main.layout>
