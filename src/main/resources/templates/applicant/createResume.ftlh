<#import "../layout.ftlh" as main>
<@main.layout ; spring>
    <form action="/resumes/create" method="post">
        <#if _csrf??>
            <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                   value="${(_csrf.token)!'csrf-token'}"/>
        </#if>

        <div class="mb-3">
            <label for="form3Example3cg" class="form-label"><@spring.message "resume.createResume.resumeName" /></label>
            <@spring.formInput "resumeDto.name" 'id="form3Example3cg" class="form-control"' />
            <@spring.showErrors "<br>" "error text-danger" />
        </div>
        <div class="mb-3">
            <label for="disabledSelect" class="form-label"><@spring.message "vacancy.form.category" /></label>
            <select name="categoryId" id="disabledSelect" class="form-select">
                <option><@spring.message "vacancy.form.chooseCategory" /></option>
                <#if categories??>
                    <#list categories as category>
                        <option value="${category.id}">${category.name}</option>
                    </#list>
                </#if>
            </select>
            <@spring.showErrors "<br>" "error text-danger" />
        </div>
        <div class="mb-3">
            <label for="exampleInputPassword1" class="form-label"><@spring.message "vacancy.form.salary" /></label>
            <@spring.formInput "resumeDto.salary" 'id="exampleInputPassword1" class="form-control"' />
            <@spring.showErrors "<br>" "error text-danger" />
        </div>

        <div class="card mb-3 about_me">
            <div class="card-body">
                <p class="mb-3"><@spring.message "resume.createResume.aboutMy" /></p>

                <div class="mb-2">
                    <label for="workYear" class="form-label"><@spring.message "resume.createResume.workExp" /></label>
                    <@spring.formInput "resumeDto.workExperience.years" 'id="workYear" class="form-control"' />
                    <@spring.showErrors "<br>" "error text-danger" />
                </div>

                <div class="mb-2">
                    <label for="companyName" class="form-label"><@spring.message "resume.createResume.companyName" /></label>
                    <@spring.formInput "resumeDto.workExperience.companyName" 'id="companyName" class="form-control"' />
                    <@spring.showErrors "<br>" "error text-danger" />
                </div>

                <div class="mb-2">
                    <label for="position" class="form-label"><@spring.message "resume.createResume.position" /></label>
                    <@spring.formInput "resumeDto.workExperience.position" 'id="position" class="form-control"' />
                    <@spring.showErrors "<br>" "error text-danger" />
                </div>

                <div class="mb-2">
                    <label for="responsibilities" class="form-label"><@spring.message "resume.createResume.duty" /></label>
                    <@spring.formInput "resumeDto.workExperience.responsibilities" 'id="responsibilities" class="form-control"' />
                    <@spring.showErrors "<br>" "error text-danger" />
                </div>
            </div>
        </div>

        <div class="card education">
            <div class="card-body">
                <div class="mb-2">
                    <label for="degree" class="form-label"><@spring.message "resume.createResume.education" /></label>
                    <@spring.formInput "resumeDto.education.degree" 'id="degree" class="form-control"' />
                    <@spring.showErrors "<br>" "error text-danger" />
                </div>

                <div class="mb-2">
                    <label for="university" class="form-label"><@spring.message "resume.createResume.whereDidYouStudy" /></label>
                    <@spring.formInput "resumeDto.education.institution" 'id="university" class="form-control"' />
                    <@spring.showErrors "<br>" "error text-danger" />
                </div>

                <div class="mb-2">
                    <label for="startDate" class="form-label"><@spring.message "resume.createResume.startOfStudy" /></label>
                    <@spring.formInput "resumeDto.education.startDate" 'id="startDate" class="form-control" placeholder="2020-05-25"' />
                    <@spring.showErrors "<br>" "error text-danger" />
                </div>

                <div class="mb-2">
                    <label for="endDate" class="form-label"><@spring.message "resume.createResume.endOfStudy" /></label>
                    <@spring.formInput "resumeDto.education.endDate" 'id="endDate" class="form-control" placeholder="2020-05-25"' />
                    <@spring.showErrors "<br>" "error text-danger" />
                </div>

            </div>
        </div>

        <div class="mb-3 contacts">
            <label class="form-label"><@spring.message "resume.createResume.linkToSN" /></label>
            <div class="d-flex align-items-center mb-2">
                <img src="https://img.icons8.com/ios-glyphs/30/new-post.png" class="me-2" alt="email">
                <input type="hidden" name="contacts[0].typeId" value="1">
                <input type="email" name="contacts[0].value" class="form-control" placeholder="example@mail.com">
            </div>
            <div class="d-flex align-items-center mb-2">
                <img src="https://img.icons8.com/ios-glyphs/30/phone.png" class="me-2" alt="phone">
                <input type="hidden" name="contacts[1].typeId" value="2">
                <input type="tel" name="contacts[1].value" class="form-control" placeholder="+996 999 555 555">
            </div>
            <div class="d-flex align-items-center mb-2">
                <img src="https://img.icons8.com/ios-filled/30/facebook.png" class="me-2" alt="fb">
                <input type="hidden" name="contacts[2].typeId" value="4">
                <input type="text" name="contacts[2].value" class="form-control">
            </div>
            <div class="d-flex align-items-center mb-2">
                <img src="https://img.icons8.com/ios-filled/30/linkedin.png" class="me-2" alt="linkedin">
                <input type="hidden" name="contacts[3].typeId" value="5">
                <input type="text" name="contacts[3].value" class="form-control">
            </div>
            <div class="d-flex align-items-center mb-2">
                <img src="https://img.icons8.com/ios-filled/30/telegram-app.png" class="me-2" alt="telegram">
                <input type="hidden" name="contacts[4].typeId" value="3">
                <input type="text" name="contacts[4].value" class="form-control">
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

            <label class="form-check-label" for="isActiveCheckbox"><@spring.message "vacancy.form.active" /></label>
        </div>
        <button type="submit" class="btn btn-primary"><@spring.message "vacancy.form.publish" /></button>
    </form>

</@main.layout>