<#import "../layout.ftlh" as main>
<@main.layout ; spring>
    <style>

        h2 {
            color: red;
            text-align: center;
            margin-bottom: 30px;
        }
        .form-section {
            margin-bottom: 20px;
        }
        .experience-range input {
            width: 80px;
            display: inline-block;
        }

    </style>

    <form action="/publications/create" method="post" enctype="multipart/form-data">
        <#if _csrf??>
            <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                   value="${(_csrf.token)!'csrf-token'}"/>
        </#if>
        <div class="mb-3">
            <label for="vacancyName" class="form-label">Title</label>
            <@spring.formInput "publicationDto.title" 'id="vacancyName" class="form-control"' />
            <@spring.showErrors "publicationDto.title" "error text-danger" />
        </div>

        <div class="mb-3">
            <label for="disabledSelect" class="form-label">Category</label>
            <select name="categoryId" id="disabledSelect" class="form-select">
                <option>Choose category</option>
                <#if categories??>
                    <#list categories as category>
                        <option value="${category.id}">${category.name}</option>
                    </#list>
                </#if>
            </select>
            <@spring.showErrors "<br>" "error text-danger" />
        </div>

        <div class="mb-3">
            <label for="vacancySalary" class="form-label">Description</label>
            <@spring.formInput "publicationDto.description" 'id="vacancySalary" class="form-control"' />
            <@spring.showErrors "publicationDto.description" "error text-danger" />
        </div>

        <div class="mb-3">
            <label for="formFileMultiple" class="form-label">Avatar</label>
            <input name="avatar" class="form-control" type="file" id="formFileMultiple">

            <@spring.bind "publicationDto.avatar" />
            <#if spring.status.errorMessages?size gt 0>
                <div class="text-danger">
                    <#list spring.status.errorMessages as error>
                        ${error}<br>
                    </#list>
                </div>
            </#if>
        </div>


        <button type="submit" class="btn btn-success w-100 ">save</button>
    </form>




</@main.layout>