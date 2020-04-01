package ru.catstack.todo.data

import ru.catstack.todo.model.Task

class TestTodoRepository : TodoRepository {
    override fun loadTodoList(): ArrayList<Task> {
        return loadTestData()
    }

    private fun loadEmpty() = ArrayList<Task>()

    private fun loadTestData() = arrayListOf(
        Task("Купить яблоки", false),
        Task("Выполнить тестовое задание", true)
    )
}