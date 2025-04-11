<#import "../layout.ftlh" as main>
<@main.layout>
    <style>

        h2 {
            color: red;
            text-align: center;
            margin-bottom: 30px;
        }
    </style>

    <h2>Login</h2>

    <form action="/auth/login" method="post">
        <div class="mb-3">
            <label class="form-label">Email:</label>
            <input name="username" type="email" class="form-control" placeholder="example@mail.com">
        </div>

        <div class="mb-3">
            <label class="form-label">Password:</label>
            <input name="password" type="password" class="form-control" placeholder="Enter password">
        </div>


        <button type="submit" class="btn btn-success w-100">Login</button>
    </form>
</@main.layout>