<#import "spring.ftl" as spring>
<#macro layout>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <title>Document</title>
    </head>
<body>

    <#assign known = SPRING_SECURITY_CONTEXT?? >
    <#if known>
        <#assign
        user = SPRING_SECURITY_CONTEXT.authentication.principal
        name = user.getUsername()
        >
    </#if>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="/">Navbar</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/">Home</a>
                </li>
<#--                <li class="nav-item">-->
<#--                    <a class="nav-link" href="/users/profile">Profile</a>-->
<#--                </li>-->
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Vacancies
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="/vacancies">Vacancies</a></li>
                    </ul>
                </li>

                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Resumes
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="/resumes">Resumes</a></li>
                    </ul>
                </li>

            </ul>
<#--            <a href="/auth/login" class="btn btn-outline-success ">Login</a>-->
<#--            <a href="/auth/register" class="btn btn-outline-success ms-4">Register</a>-->
            <#if user??>
                <a href="/users/profile" class="nav-link mr4-l">
                    Profile
                    <#--                <#list user.authorities as auth>-->
                    <#--                    <span class="text-danger">${auth}</span>-->
                    <#--                    <#if auth?has_next>-->
                    <#--                        /-->
                    <#--                    </#if>-->
                    <#--                </#list>-->
                </a>
                <#if _csrf??>
                    <form action="/logout" method="post">
                        <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                               value="${(_csrf.token)!'--no-token--'}"/>
                        <button type="submit" class="btn btn-outline-info m-2"
                                id="logout">Logout
                        </button>
                    </form>
                <#else>
                    <a href="/logout" class="btn btn-outline-info m-2"
                       id="logout">Logout</a>
                </#if>
            <#else>
                <a href="/auth/login"
                   class="btn btn-outline-info m-2">Login</a>
                <a href="/auth/register"
                   class="btn btn-outline-info m-2">Register</a>
            </#if>
        </div>
    </div>
</nav>

    <div class="container">
        <#nested spring>
    </div>
</#macro>