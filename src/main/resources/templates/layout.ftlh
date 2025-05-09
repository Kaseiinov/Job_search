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

<#--    <#assign isApplicant = false>-->
<#--    <#list user.roles as role>-->
<#--        <#if role.name?lower_case == "applicant">-->
<#--            <#assign isApplicant = true>-->
<#--        </#if>-->
<#--    </#list>-->

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="/">JobSearch</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/"><@spring.message "layout.nave.home" /></a>
                </li>
<#--                <li class="nav-item">-->
<#--                    <a class="nav-link active" aria-current="page" href="/">${user}</a>-->
<#--                </li>-->

<#--                <li class="nav-item">-->
<#--                    <a class="nav-link" href="/users/profile">Profile</a>-->
<#--                </li>-->
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false"><@spring.message "layout.nave.vacancies" /></a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="/vacancies"><@spring.message "layout.nave.vacancies" /></a></li>
                    </ul>
                </li>

<#--                <#if user?? && user.accountType?? && user.accountType?lower_case != "applicant">-->
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false"><@spring.message "layout.nave.resumes" /></a>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <li><a class="dropdown-item" href="/resumes"><@spring.message "layout.nave.resumes" /></a></li>
                        </ul>
                    </li>
<#--                </#if>-->

                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false"><@spring.message "layout.nav.lang.exchange" /></a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="?lang=en"><@spring.message "layout.nav.lang_en" /></a></li>
                        <li><a class="dropdown-item" href="?lang=ru"><@spring.message "layout.nav.lang_ru" /></a></li>
                    </ul>
                </li>

            </ul>
<#--            <a href="/auth/login" class="btn btn-outline-success ">Login</a>-->
<#--            <a href="/auth/register" class="btn btn-outline-success ms-4">Register</a>-->
            <#if user??>
                <a href="/users/profile" class="nav-link mr4-l">
                    <@spring.message "layout.nave.profile" />
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
                                id="logout"><@spring.message "layout.nave.logout" />
                        </button>
                    </form>
                <#else>
                    <a href="/logout" class="btn btn-outline-info m-2"
                       id="logout"><@spring.message "layout.nave.logout" /></a>
                </#if>
            <#else>
                <a href="/auth/login"
                   class="btn btn-outline-info m-2"><@spring.message "layout.nave.login" /></a>
                <a href="/auth/register"
                   class="btn btn-outline-info m-2"><@spring.message "layout.nave.register" /></a>
            </#if>
        </div>
    </div>
</nav>

    <div class="container">
        <#nested spring>
    </div>
</#macro>