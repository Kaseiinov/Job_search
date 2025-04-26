<#import "../layout.ftlh" as main>
<@main.layout ; spring>

    <form action="/resumes/edit" method="post" >
        <#if _csrf??>
            <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                   value="${(_csrf.token)!'csrf-token'}"/>
        </#if>
        <input type="hidden" name="id" value="${resumeDto.id}" />

        <div class="mb-3">
            <label for="form3Example3cg" class="form-label">Название резюме</label>
            <@spring.formInput "resumeDto.name" 'id="form3Example3cg" class="form-control"' />
            <@spring.showErrors "<br>" "error text-danger" />
        </div>
        <div class="mb-3">
            <label for="disabledSelect" class="form-label">Категория</label>
            <select name="categoryId" id="disabledSelect" class="form-select">
                <option>Выберите категорию</option>
                <#if categories??>
                    <#list categories as category>
                        <option value="${category.id}">${category.name}</option>
                    </#list>
                </#if>
            </select>
            <@spring.showErrors "<br>" "error text-danger" />
        </div>
        <div class="mb-3">
            <label for="exampleInputPassword1" class="form-label">Ожидаемый уровень оклада</label>
            <@spring.formInput "resumeDto.salary" 'id="exampleInputPassword1" class="form-control"' />
            <@spring.showErrors "<br>" "error text-danger" />
        </div>

        <div class="mb-3 form-check">
            <input type="hidden" name="_isActive" value="on">

            <input type="checkbox"
                   name="isActive"
                   class="form-check-input"
                   id="isActiveCheckbox"
                   value="true"
                    ${(resumeDto.isActive?? && resumeDto.isActive)?then('checked', '')}>

            <label class="form-check-label" for="isActiveCheckbox">Активно</label>
        </div>
        <button type="submit" class="btn btn-primary">Редактировать</button>
    </form>

</@main.layout>