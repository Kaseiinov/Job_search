<#import "../layout.ftlh" as main>
<@main.layout ; spring>

    <body class="bg-light">

    <div class="text-center mb-4">
        <h2 style="color: red;">
            <@spring.message "auth.profile.personalAcc" />
            <#if user.accountType == "EMPLOYER">
                <@spring.message "auth.profile.employer" />
            <#elseif user.accountType == "APPLICANT">
                <@spring.message "auth.profile.applicant" />
            </#if>
        </h2>
    </div>

    <div class="d-flex align-items-center justify-content-between mb-4">
        <div class="d-flex align-items-center">

            <#if user.avatar??>
                <img src="/images/byName/${user.avatar.fileName!}" width="80" height="80"
                     alt="<@spring.message "auth.form.avatar" />" class="me-3">
            <#else>
                <i class="fas fa-user-circle me-3 rounded" style="font-size: 80px; color: #6c757d;"></i>
            </#if>

            <div>
                <#if user.accountType == "EMPLOYER">
                    <h5>${user.name!}</h5>
                <#elseif user.accountType == "APPLICANT">
                    <h5>${user.name!}</h5>
                    <p>Возраст: ${user.age!}</p>
                </#if>
                <a href="/users/edit/${user.email}"
                   class="btn btn-outline-success btn-sm mt-2"><@spring.message "auth.profile.edit" /></a>
            </div>
        </div>

        <div>
            <#if user.accountType == "APPLICANT">
                <a href="/resumes/create"
                   class="btn btn-outline-danger">+ <@spring.message "auth.profile.createResume" /></a>
            <#elseif user.accountType == "EMPLOYER">
                <a href="/vacancies/create"
                   class="btn btn-outline-danger">+ <@spring.message "auth.profile.createVacancy" /></a>
            </#if>
            <a href="/users/profile/publications"
               class="btn btn-outline-danger">My publication</a>
            <a href="/publications/create"
               class="btn btn-outline-danger">Create publication</a>
            <#if user.accountType == "APPLICANT">
                <a href="#" class="btn btn-outline-secondary position-relative ms-3">
                    <@spring.message "auth.profile.responses" />
                    <#--                        <#if responsesCount?? && responsesCount > 0>-->
                    <#--                            <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">-->
                    <#--                            ${responsesCount}-->
                    <#--                        </span>-->
                    <#--                        </#if>-->
                </a>
            </#if>
        </div>
    </div>

    <div class="mb-3">
        <h5>
            <#if user.accountType == "EMPLOYER"><@spring.message "auth.profile.createdVacancy" /><#else><@spring.message "auth.profile.resume" /></#if>
        </h5>

        <div class="row">
            <#list items as item>
                <div class="col-md-4">
                    <div class="card mb-3 shadow-sm">
                        <div class="card-body">
                            <h6 class="card-title">${item.name}</h6>
                            <p class="card-text text-muted">
                                <@spring.message "auth.profile.dateUpdate" /> ${item.updateTimeFormatted!""}
                            </p>
                            <#if user.accountType == "APPLICANT">
                                <div class="d-flex justify-content-between">
                                    <a href="#"
                                       class="btn btn-outline-primary btn-sm"><@spring.message "auth.profile.update" /></a>
                                    <a href="/resumes/edit/${item.id}"
                                       class="btn btn-outline-success btn-sm"><@spring.message "auth.profile.edit" /></a>
                                </div>
                            <#elseif user.accountType == "EMPLOYER">
                                <div class="d-flex justify-content-between">
                                    <a href="#"
                                       class="btn btn-outline-primary btn-sm"><@spring.message "auth.profile.update" /></a>
                                    <a href="/vacancies/edit/${item.id}"
                                       class="btn btn-outline-success btn-sm"><@spring.message "auth.profile.edit" /></a>
                                </div>
                            </#if>

                        </div>
                    </div>
                </div>
            </#list>
        </div>
    </div>

    <#if vacancyItems??>
        <#if vacancyItems.getTotalElements() gt 0>
            <nav id="navigation" aria-label="..." class="d-flex justify-content-center">
                <ul class="pagination">
                    <li class="page-item <#if vacancyItems.first>disabled</#if>">
                        <a class="page-link" href="?page=${vacancyItems.getNumber()-1}&size=${vacancyItems.size}">
                            previous
                        </a>
                    </li>

                    <#list 1..vacancyItems.getTotalPages() as page>
                        <li class="page-item <#if page = vacancyItems.getNumber() + 1>active</#if>" aria-current="page">
                            <a class="page-link" href="?page=${page - 1}&size=${vacancyItems.size}">${page}</a>
                        </li>
                    </#list>

                    <li class="page-item <#if vacancyItems.last>disabled</#if>">
                        <a class="page-link" href="?page=${vacancyItems.getNumber()+1}&size=${vacancyItems.size}">
                            next
                        </a>
                    </li>
                </ul>
            </nav>
        </#if>
    </#if>

    <#if resumeItems??>
        <#if resumeItems.getTotalElements() gt 0>

            <nav id="navigation" aria-label="..." class="d-flex justify-content-center">
                <ul class="pagination">
                    <li class="page-item <#if resumeItems.first>disabled</#if>">
                        <a class="page-link" href="?page=${resumeItems.getNumber()-1}&size=${resumeItems.size}">
                            previous
                        </a>
                    </li>

                    <#list 1..resumeItems.getTotalPages() as page>
                        <li class="page-item <#if page = resumeItems.getNumber() + 1>active</#if>" aria-current="page">
                            <a class="page-link" href="?page=${page - 1}&size=${resumeItems.size}">${page}</a>
                        </li>
                    </#list>

                    <li class="page-item <#if resumeItems.last>disabled</#if>">
                        <a class="page-link" href="?page=${resumeItems.getNumber()+1}&size=${resumeItems.size}">
                            next
                        </a>
                    </li>
                </ul>
            </nav>
        </#if>
    </#if>


    </body>

</@main.layout>
