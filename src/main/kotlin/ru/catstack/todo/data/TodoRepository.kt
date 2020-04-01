package ru.catstack.todo.data

import ru.catstack.todo.model.Task

interface TodoRepository {
    fun loadTodoList(): List<Task>
}