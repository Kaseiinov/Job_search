<#import "../layout.ftlh" as main>
<@main.layout ; spring>>
    <form action="/" method="get">
        <div class="mb-3">
            <label for="exampleInputEmail1" class="form-label">By name</label>
            <input type="hidden" name="sortBy" value="byName">
            <input type="text" name="name" class="form-control" id="exampleInputEmail1">
        </div>
        <div class="mb-3">
            <label for="exampleInputPassword1" class="form-label">By salary from</label>
            <input type="hidden" name="sortBy" value="bySalary">
            <input type="number" name="salaryFrom" class="form-control" id="exampleInputPassword1">
        </div>

        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</@main.layout>