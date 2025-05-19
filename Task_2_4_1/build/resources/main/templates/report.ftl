<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Build Results</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        table {
            border-collapse: collapse;
            margin-bottom: 20px;
            width: 100%;
        }
        caption {
            font-size: 1.2em;
            font-weight: bold;
            padding: 10px;
            background-color: #f0f0f0;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f4f4f4;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        .points {
            font-weight: bold;
            color: #2196F3;
        }
    </style>
</head>
<body>
    <h1>Student Build Results</h1>
    
    <#list results?keys as task>
    <table>
        <caption>${task}</caption>
        <tr>
            <th>Student</th>
            <th>Build</th>
            <th>Tests</th>
            <th>Points</th>
        </tr>
        <#list results[task]?keys as student>
        <tr>
            <td>${student}</td>
            <td>${results[task][student].buildStatus}</td>
            <td>${results[task][student].testStatus}</td>
            <td class="points">${results[task][student].points}</td>
        </tr>
        </#list>
    </table>
    </#list>
</body>
</html> 