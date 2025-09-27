<#import "../layout.ftlh" as main>
<@main.layout ; spring>

    <form method="get" action="/" class="d-flex justify-content-end mb-3">
        <select name="sortBy" class="form-select w-auto" onchange="this.form.submit()">
            <option value=""><@spring.message "index.form.sortBy" /></option>
            <option value="dateDesc" <#if sortBy == "dateDesc">selected</#if>><@spring.message "index.form.newestFirst" /></option>
            <option value="dateAsc" <#if sortBy == "dateAsc">selected</#if>><@spring.message "index.form.oldestFirst" /></option>
        </select>
    </form>

    <#if publications??>
        <#list publications as publication>
            <div class="card mx-auto mb-3 mt-3" style="width: 800px; height: 400px;">
                <div style="height: 450px; overflow: hidden;">
                    <img src="images/byName/${publication.avatarString}"
                         class="card-img-top"
                         alt="publication"
                         style="width: 100%; height: 100%; object-fit: cover;">
                </div>
                <div class="card-body" style="height: 250px;">
                    <h5 class="card-title">${publication.title}</h5>
                    <p class="card-text">${publication.description}</p>
                    <p class="card-text"><small class="text-muted">${publication.publicationDate}</small></p>
                    <p class="card-text"><small class="text-muted">${publication.updateDate}</small></p>
                    <a href="#" class="btn btn-primary">Read More</a>
                </div>
            </div>
        </#list>
    </#if>

    <#if items.getTotalElements() gt 0>
        <nav id="navigation" aria-label="..." class="d-flex justify-content-center" >
            <ul class="pagination" >
                <li class="page-item <#if items.first>disabled</#if>">
                    <a class="page-link" href="?page=${items.getNumber()-1}&size=${items.size}">
                        previous
                    </a>
                </li>

                <#list 1..items.getTotalPages() as page>
                    <li class="page-item <#if page = items.getNumber() + 1>active</#if>" aria-current="page">
                        <a class="page-link" href="?page=${page - 1}&size=${items.size}">${page}</a>
                    </li>
                </#list>

                <li class="page-item <#if items.last>disabled</#if>">
                    <a class="page-link" href="?page=${items.getNumber()+1}&size=${items.size}">
                        next
                    </a>
                </li>
            </ul>
        </nav>
    </#if>


</@main.layout>