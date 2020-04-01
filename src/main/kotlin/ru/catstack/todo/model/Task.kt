package ru.catstack.todo.model

data class Task(
    val taskText: String,
    var isCompleted: Boolean
)