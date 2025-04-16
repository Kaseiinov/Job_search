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

    <form action="/users/edit" method="post">
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

<#--        <div class="mb-3">-->
<#--            <label class="form-label">Account type:</label>-->
<#--            <div>-->
<#--                <div class="form-check form-check-inline">-->
<#--                    <input class="form-check-input" type="radio" name="accountType" id="applicant" value="applicant" checked>-->
<#--                    <label class="form-check-label" for="applicant">Applicant</label>-->
<#--                </div>-->
<#--                <div class="form-check form-check-inline">-->
<#--                    <input class="form-check-input" type="radio" name="accountType" id="employer" value="employer">-->
<#--                    <label class="form-check-label" for="employer">Employer</label>-->
<#--                </div>-->
<#--            </div>-->
<#--        </div>-->


        <button type="submit" class="btn btn-success w-100">Сохранить</button>
    </form>
</@main.layout>