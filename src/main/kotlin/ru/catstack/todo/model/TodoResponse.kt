package ru.catstack.todo.model

data class TodoResponse(val totalCount: Int, val tasks: ArrayList<Task>)