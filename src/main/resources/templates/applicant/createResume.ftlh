<#import "../layout.ftlh" as main>
<@main.layout ; spring>
    <form action="/resumes/create" method="post">
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
        </div>
        <div class="mb-3">
            <label for="exampleInputPassword1" class="form-label">Ожидаемый уровень оклада</label>
            <@spring.formInput "resumeDto.salary" 'id="exampleInputPassword1" class="form-control"' />
            <@spring.showErrors "<br>" "error text-danger" />
        </div>

        <div class="card mb-3">
            <div class="card-body">
                <p class="mb-3">О себе</p>

                <div class="mb-2">
                    <label for="workYear" class="form-label">Год работы:</label>
                    <@spring.formInput "resumeDto.workExperience.years" 'id="workYear" class="form-control"' />
                    <@spring.showErrors "<br>" "error text-danger" />
                </div>

                <div class="mb-2">
                    <label for="companyName" class="form-label">Название компании:</label>
                    <@spring.formInput "resumeDto.workExperience.companyName" 'id="companyName" class="form-control"' />
                    <@spring.showErrors "<br>" "error text-danger" />
                </div>

                <div class="mb-2">
                    <label for="position" class="form-label">Должность:</label>
                    <@spring.formInput "resumeDto.workExperience.position" 'id="position" class="form-control"' />
                    <@spring.showErrors "<br>" "error text-danger" />
                </div>

                <div class="mb-2">
                    <label for="responsibilities" class="form-label">Обязанности:</label>
                    <@spring.formInput "resumeDto.workExperience.responsibilities" 'id="responsibilities" class="form-control"' />
                    <@spring.showErrors "<br>" "error text-danger" />
                </div>
            </div>
        </div>

        <div class="card">
            <div class="card-body">
                <div class="mb-2">
                    <label for="degree" class="form-label">Образование:</label>
                    <@spring.formInput "resumeDto.education.degree" 'id="degree" class="form-control"' />
                    <@spring.showErrors "<br>" "error text-danger" />
                </div>

                <div class="mb-2">
                    <label for="university" class="form-label">Где учился:</label>
                    <@spring.formInput "resumeDto.education.institution" 'id="university" class="form-control"' />
                    <@spring.showErrors "<br>" "error text-danger" />
                </div>

                <div class="mb-2">
                    <label for="eduPeriod" class="form-label">Срок обучения:</label>
                    <@spring.formInput "resumeDto.education.period" 'id="eduPeriod" class="form-control" placeholder="2020-2024"' />
                    <@spring.showErrors "<br>" "error text-danger" />
                </div>

            </div>
        </div>

        <div class="mb-3">
            <label class="form-label">Ссылки на соц. сети:</label>
            <div class="d-flex align-items-center mb-2">
                <img src="https://img.icons8.com/ios-glyphs/30/new-post.png" class="me-2" alt="email">
                <input type="email" class="form-control" placeholder="example@mail.com">
            </div>
            <div class="d-flex align-items-center mb-2">
                <img src="https://img.icons8.com/ios-glyphs/30/phone.png" class="me-2" alt="phone">
                <input type="tel" class="form-control" placeholder="+996 999 555 555">
            </div>
            <div class="d-flex align-items-center mb-2">
                <img src="https://img.icons8.com/ios-filled/30/facebook.png" class="me-2" alt="fb">
                <input type="text" class="form-control">
            </div>
            <div class="d-flex align-items-center mb-2">
                <img src="https://img.icons8.com/ios-filled/30/linkedin.png" class="me-2" alt="linkedin">
                <input type="text" class="form-control">
            </div>
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
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>

</@main.layout>