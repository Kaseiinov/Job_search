<#import "../layout.ftlh" as main>
<@main.layout>

    <style>
        .card {
            border-radius: 25px;
        }

    </style>

        <h2 class="mb-4 text-center">Список резюме</h2>

    <form method="get" action="/resumes" class="d-flex justify-content-end mb-3">
        <select name="sortBy" class="form-select w-auto" onchange="this.form.submit()">
            <option value="">Сортировать по</option>
            <option value="dateDesc" <#if sortBy == "dateDesc">selected</#if>>Сначала новые</option>
            <option value="dateAsc" <#if sortBy == "dateAsc">selected</#if>>Сначала старые</option>
        </select>
    </form>

        <#list resumes as resume>

            <div class="card p-3 mb-4" >
                <div class="d-flex align-items-start">
                    <#if resume.user.avatar?? && resume.user.avatar.fileName??>
                        <img src="/images/byName/${resume.user.avatar.fileName}"
                             width="80"
                             height="80"
                             alt="avatar"
                             class="me-3 rounded">
                    <#else>
                        <i class="fas fa-user-circle me-3 rounded" style="font-size: 80px; color: #6c757d;"></i>
                    </#if>
                    <div class="flex-grow-1">
                        <h5 class="mb-1">${resume.name}</h5>
                        <#if resume.user.age??>
                            <div class="text-muted small">
                                ${resume.user.age} года • <span class="text-success">Обновлено ${resume.updateTime!}</span>
                            </div>
                        <#else>
                            <div class="text-muted small">
                                <span class="text-success">Обновлено ${resume.updateTime!}</span>
                            </div>
                        </#if>

                    </div>
                </div>

                <hr>

                <div class="small mb-2">
                    <strong>Опыт работы</strong><br>
                    <#if resume.workExperience?? && resume.workExperience.years??>
                        ${resume.workExperience.years}
                    <#else>
                        нет
                    </#if>
                </div>

                <#if resume.workExperience?? && resume.workExperience.companyName??>
                    <div class="small mb-2">
                        <strong>Последнее место работы</strong><br>
                        <span class="fw-bold">${resume.workExperience.companyName}</span><br>
                        <a href="#" class="text-primary small">Подробнее о последнем месте</a>
                    </div>
                </#if>


            </div>


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