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
        <input type="hidden" name="id" value="${userDto.id}">
        <input type="hidden" name="email" value="${userDto.email}">
        <input type="hidden" name="accountType" value="${userDto.accountType}">

        <div class="mb-3">
            <label class="form-label">Name:</label>
            <@spring.formInput "userDto.name" 'class="form-control form-control-lg"' />
            <@spring.showErrors "<br>" "error text-danger" />
        </div>

        <div class="mb-3">
            <label class="form-label">Surname:</label>
            <@spring.formInput "userDto.surname" 'class="form-control form-control-lg"' />
            <@spring.showErrors "<br>" "error text-danger" />
        </div>

        <div class="mb-3">
            <label class="form-label">age:</label>
            <@spring.formInput "userDto.age" 'class="form-control form-control-lg"' />
            <@spring.showErrors "<br>" "error text-danger" />
        </div>

        <div class="mb-3">
            <label for="form3Example4cg" class="form-label">Password:</label>
            <@spring.formPasswordInput "userDto.password" 'id="form3Example4cg" class="form-control form-control-lg"' />
            <@spring.showErrors "<br>" "error text-danger" />
        </div>

        <div class="mb-3">
            <label class="form-label">Phone number:</label>
            <@spring.formInput "userDto.phoneNumber" 'class="form-control form-control-lg"' />
            <@spring.showErrors "<br>" "error text-danger" />
        </div>

        <div class="mb-3">
            <label for="formFileMultiple" class="form-label">Photo</label>
            <input type="hidden" name="userId" value="${userDto.id}">
            <input name="avatar.file" class="form-control" type="file" id="formFileMultiple">
            <@spring.showErrors "<br>" "error text-danger" />

        </div>

        <button type="submit" class="btn btn-success w-100 mb-3">Сохранить</button>
    </form>
</@main.layout>