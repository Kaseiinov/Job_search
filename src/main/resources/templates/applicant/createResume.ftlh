<#import "../layout.ftlh" as main>
<@main.layout>
    <style>

            h2 {
                color: red;
                text-align: center;
                margin-bottom: 30px;
            }
            .form-section {
                margin-bottom: 20px;
            }
            .form-box {
                border: 1px solid #ccc;
                border-radius: 10px;
                padding: 15px;
                position: relative;
            }
            .add-btn {
                position: absolute;
                top: 10px;
                right: 10px;
                font-size: 24px;
                background: #d0f0f7;
                border-radius: 50%;
                width: 40px;
                height: 40px;
                text-align: center;
                line-height: 36px;
                cursor: pointer;
            }
            .add-btn.education {
                background: #e0d0f7;
            }
        </style>
        <h2>Создание резюме:</h2>

        <div class="mb-3">
            <label class="form-label">Название резюме:</label>
            <input type="text" class="form-control" placeholder="Введите название">
        </div>

        <div class="mb-3">
            <label class="form-label">Категория:</label>
            <select class="form-select">
                <option selected>Выберите категорию</option>
            </select>
        </div>

        <div class="mb-3">
            <label class="form-label">Ожидаемый уровень оклада:</label>
            <input type="text" class="form-control" placeholder="Введите оклад">
        </div>

        <div class="form-section">
            <label class="form-label">О себе:</label>

            <div class="form-box mb-3">
                <p>Год работы: ___________</p>
                <p>Название компании: ___________</p>
                <p>Должность: ___________</p>
                <p>Обязанности: ___________</p>
                <div class="add-btn">+</div>
            </div>

            <div class="form-box">
                <p>Образование: ___________</p>
                <p>Где учился: ___________</p>
                <p>Срок обучения: ___________</p>
                <div class="add-btn education">+</div>
            </div>
        </div>

        <div class="form-section">
            <label class="form-label">Ссылки на соц. сети:</label>
            <div class="mb-2 d-flex gap-2">
                <input type="text" class="form-control" placeholder="example@mail.com">
                <input type="text" class="form-control" placeholder="Facebook">
            </div>
            <div class="mb-2 d-flex gap-2">
                <input type="text" class="form-control" placeholder="example.com/username">
                <input type="text" class="form-control" placeholder="LinkedIn">
            </div>
            <div class="mb-3">
                <input type="text" class="form-control" placeholder="+996 999 555 555">
            </div>
        </div>

        <div class="form-check mb-3">
            <input class="form-check-input" type="checkbox" value="" id="activeCheck" checked>
            <label class="form-check-label" for="activeCheck">
                Активно
            </label>
        </div>

        <a class="btn btn-success w-100">Опубликовать</a>

</@main.layout>