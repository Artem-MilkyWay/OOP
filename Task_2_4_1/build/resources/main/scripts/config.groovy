package scripts

/**
 * Unified configuration file containing all project settings.
 * Includes student information and task list.
 */

binding.setVariable("config", [
    students: [
        "Artem-MilkyWay": [
            username: "Artem-MilkyWay",
            repository: "https://github.com/Artem-MilkyWay/OOP"
        ],
        "Andrew-Vetrov": [
            username: "Andrew-Vetrov",
            repository: "https://github.com/Andrew-Vetrov/OOP"
        ],
        "Hom4ikTop4ik": [
            username: "Hom4ikTop4ik",
            repository: "https://github.com/Hom4ikTop4ik/OOP"
        ]
    ],
    tasks: [
        "Task_1_1_1",
        "Task_1_1_2",
        "Task_1_1_3",
        "Task_1_2_1",
        "Task_1_2_2",
        "Task_1_3_1",
        "Task_1_4_1",
        "Task_1_5_1"
    ]
]) 