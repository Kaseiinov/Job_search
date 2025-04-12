<#import "../layout.ftlh" as main>
<@main.layout>
    <style>

        h2 {
            color: red;
            text-align: center;
            margin-bottom: 30px;
        }
    </style>

    <h2>Иземенение пользователя</h2>

    <form action="/users/update" method="post">
        <div class="mb-3">
            <label class="form-label">Name:</label>
            <input name="name" type="text" class="form-control" placeholder="Введите имя">
        </div>

        <div class="mb-3">
            <label class="form-label">Surname:</label>
            <input name="surname" type="text" class="form-control" placeholder="Введите surname">
        </div>

        <div class="mb-3">
            <label class="form-label">age:</label>
            <input name="age" type="number" class="form-control" placeholder="Enter age">
        </div>

        <div class="mb-3">
            <label class="form-label">Password:</label>
            <input name="password" type="password" class="form-control" placeholder="Enter password">
        </div>

        <div class="mb-3">
            <label class="form-label">Phone number:</label>
            <input name="phoneNumber" type="tel" class="form-control" placeholder="+996 999 555 555">
        </div>

        <div class="mb-3">
            <label class="form-label">Account type:</label>
            <div>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" name="accountType" id="applicant" value="applicant" checked>
                    <label class="form-check-label" for="applicant">Applicant</label>
                </div>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" name="accountType" id="employer" value="employer">
                    <label class="form-check-label" for="employer">Employer</label>
                </div>
            </div>
        </div>


        <button type="submit" class="btn btn-success w-100">Сохранить</button>
    </form>
</@main.layout>