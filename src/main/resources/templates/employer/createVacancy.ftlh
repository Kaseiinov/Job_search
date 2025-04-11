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
        .experience-range input {
            width: 80px;
            display: inline-block;
        }
        .btn-publish {
            background: repeating-linear-gradient(135deg, #d3f4e2, #d3f4e2 10px, #b0e9d1 10px, #b0e9d1 20px);
        }
    </style>
        <h2>Создание вакансии:</h2>

        <div class="mb-3">
            <label class="form-label">Название вакансии:</label>
            <input type="text" class="form-control" placeholder="Введите название">
        </div>

        <div class="mb-3">
            <label class="form-label">Категория:</label>
            <select class="form-select">
                <option selected>Выберите категорию</option>
            </select>
        </div>

        <div class="mb-3">
            <label class="form-label">Заработная плата:</label>
            <input type="text" class="form-control" placeholder="Введите оклад">
        </div>

        <div class="mb-3">
            <label class="form-label">Описание вакансии:</label>
            <textarea class="form-control" rows="4" placeholder=""></textarea>
        </div>

        <div class="mb-3">
            <label class="form-label">Требуемый опыт:</label>
            <div class="experience-range d-flex align-items-center gap-2">
                <label>от</label>
                <input type="number" class="form-control">
                <label>до</label>
                <input type="number" class="form-control">
            </div>
        </div>

        <div class="form-check mb-3">
            <input class="form-check-input" type="checkbox" id="activeCheck" checked>
            <label class="form-check-label" for="activeCheck">
                Активно
            </label>
        </div>

        <a class="btn btn-success w-100 btn-publish">Опубликовать</a>
</@main.layout>