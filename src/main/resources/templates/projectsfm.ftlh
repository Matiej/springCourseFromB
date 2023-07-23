<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <title>Projects freemarker</title>
        <style>
            .butt {
        background-color: #4CAF50; /* Green */
        border: none;
        color: black;
        padding: 15px 32px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        font-size: 16px;
        margin: 4px 2px;
        cursor: pointer;
        }

        </style>
</head>
<body style="background-color:#BBADAA;">

<div style="text-align: center">
    <h1 style="color: red;">PROJECTS TABLE by FreeMarker template engine</h1>
    <div class="container">
        <div class="col-sm-5">
           <#list projects as project>
                <h1>${project.name}</h1>
                Tasks:
                <ol>
                <#list project.tasks as task>
                    <li>
                        ${task.name}: ${task.description}
                    </li>
                </#list>
                </ol>
            </#list>
        </div>
    </div>
    <div style="text-align: center;">
        <a class="butt" th:href="@{/view/projects/add}">Add New Project</a>
    </div>
</div>

</body>
</html>