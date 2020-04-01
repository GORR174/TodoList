package ru.catstack.todo.data

import ru.catstack.todo.model.Task

interface TodoRepository {
    fun loadTodoList(): ArrayList<Task>

    fun saveTodoList(todoList: ArrayList<Task>)
}