<#import "../layout.ftlh" as main>
<@main.layout ; spring>

    <style>
        .card {
            border-radius: 25px;
        }

    </style>

        <h2 class="mb-4 text-center"><@spring.message "resume.createResume.listOfResume" /></h2>

    <form method="get" action="/resumes" class="d-flex justify-content-end mb-3">
        <select name="sortBy" class="form-select w-auto" onchange="this.form.submit()">
            <option value=""><@spring.message "index.form.sortBy" /></option>
            <option value="dateDesc" <#if sortBy == "dateDesc">selected</#if>><@spring.message "index.form.newestFirst" /></option>
            <option value="dateAsc" <#if sortBy == "dateAsc">selected</#if>><@spring.message "index.form.oldestFirst" /></option>
        </select>
    </form>

        <#list resumes as resume>

            <div class="card p-3 mb-4" >
                <div class="d-flex align-items-start">
                    <#if resume.user.avatar?? && resume.user.avatar.fileName??>
                        <img src="/images/byName/${resume.user.avatar.fileName}"
                             width="80"
                             height="80"
                             alt="<@spring.message "auth.form.avatar" />"
                             class="me-3 rounded">
                    <#else>
                        <i class="fas fa-user-circle me-3 rounded" style="font-size: 80px; color: #6c757d;"></i>
                    </#if>
                    <div class="flex-grow-1">
                        <h5 class="mb-1">${resume.name}</h5>
                        <#if resume.user.age??>
                            <div class="text-muted small">
                                ${resume.user.age} года • <span class="text-success"><@spring.message "resume.createResume.updated" /> ${resume.updateTime!}</span>
                            </div>
                        <#else>
                            <div class="text-muted small">
                                <span class="text-success"><@spring.message "resume.createResume.updated" /> ${resume.updateTime!}</span>
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
                        <@spring.message "index.list.noExperience" />
                    </#if>
                </div>

                <#if resume.workExperience?? && resume.workExperience.companyName??>
                    <div class="small mb-2">
                        <strong><@spring.message "resume.createResume.lastPlaceOfWork" /></strong><br>
                        <span class="fw-bold">${resume.workExperience.companyName}</span><br>
                        <a href="#" class="text-primary small"><@spring.message "resume.createResume.moreAboutLastPlaceOfWork" /></a>
                    </div>
                </#if>


            </div>


        </#list>

    <nav >
        <div class="d-flex justify-content-center">
            <ul class="pagination">
                <#if hasPrevious>
                    <li class="page-item ">
                        <a class="page-link" href="/resumes?page=${currentPage - 1}" ><@spring.message "index.nav.previous" /></a>
                    </li>
                <#else>
                    <li class="page-item disabled">
                        <a class="page-link" href="/resumes?page=${currentPage - 1}" ><@spring.message "index.nav.previous" /></a>
                    </li>
                </#if>
                <#list 0..(totalPages - 1) as page>
                    <li class="page-item <#if page == currentPage>active</#if>">
                        <a class="page-link" href="/resumes?page=${page}">${page + 1}</a>
                    </li>
                </#list>
                <#if hasNext>
                    <li class="page-item">
                        <a class="page-link" href="/resumes?page=${currentPage + 1}"><@spring.message "index.nav.next" /></a>
                    </li>
                <#else>
                    <li class="page-item disabled">
                        <a class="page-link" href="/resumes?page=${currentPage + 1}"><@spring.message "index.nav.next" /></a>
                    </li>
                </#if>

            </ul>
        </div>
    </nav>

</@main.layout>