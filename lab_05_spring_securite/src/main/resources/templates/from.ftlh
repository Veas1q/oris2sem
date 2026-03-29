<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Добавить лицо</title>
</head>
<body>
<h2>Ввод данных лица</h2>
<form action="/persons/save" method="post">
    <label>Имя:</label>
    <input type="text" name="name" required/>
    <br/><br/>
    <button type="submit">Сохранить</button>
</form>
<br/>
<a href="/persons/all">К списку</a>
</body>
</html>