<#import "../layout.ftlh" as main>
<@main.layout ; spring>
    <style>

        h2 {
            color: red;
            text-align: center;
            margin-bottom: 30px;
        }
    </style>

    <h2>Иземенение пользователя</h2>

    <form action="/users/edit" method="post" enctype="multipart/form-data">
        <#if _csrf??>
            <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                   value="${(_csrf.token)!'csrf-token'}"/>
        </#if>


        <input type="hidden" name="id" value="${userEditDto.id}">
        <input type="hidden" name="email" value="${userEditDto.email}">
        <input type="hidden" name="accountType" value="${userEditDto.accountType}">
        <input type="hidden" name="password" value="${userEditDto.password}">

        <div class="mb-3">
            <label class="form-label">Name:</label>
            <@spring.formInput "userEditDto.name" 'class="form-control form-control-lg"' />
            <@spring.showErrors "<br>" "error text-danger" />
        </div>

        <div class="mb-3">
            <label class="form-label">Surname:</label>
            <@spring.formInput "userEditDto.surname" 'class="form-control form-control-lg"' />
            <@spring.showErrors "<br>" "error text-danger" />
        </div>

        <div class="mb-3">
            <label class="form-label">age:</label>
            <@spring.formInput "userEditDto.age" 'class="form-control form-control-lg"' />
            <@spring.showErrors "<br>" "error text-danger" />
        </div>

        <div class="mb-3">
            <label class="form-label">Phone number:</label>
            <@spring.formInput "userEditDto.phoneNumber" 'class="form-control form-control-lg"' />
            <@spring.showErrors "<br>" "error text-danger" />
        </div>

        <div class="mb-3">
            <label for="formFileMultiple" class="form-label">Photo</label>
            <input type="hidden" name="avatar.userId" value="${userEditDto.id}">
            <input name="avatar.file" class="form-control" type="file" id="formFileMultiple">

        </div>

        <button type="submit" class="btn btn-success w-100 mb-3">Сохранить</button>
    </form>
</@main.layout>