package ru.catstack.todo.ui

import ru.catstack.todo.data.TodoRepository
import ru.catstack.todo.model.Task

class TodoListViewModel(private val repository: TodoRepository) {
    private var mutableTodoList = ArrayList<Task>()
    val todoList: List<Task>
            get() = mutableTodoList

    fun loadTodoList() {
        if (todoList.isEmpty())
            mutableTodoList = repository.loadTodoList()
    }

    fun addTask(todoText: String) {
        mutableTodoList.add(Task(todoText, false))
        saveToRepository()
    }

    fun deleteTask(taskIndex: Int) {
        mutableTodoList.removeAt(taskIndex)
        saveToRepository()
    }

    private fun saveToRepository() {
        repository.saveTodoList(mutableTodoList)
    }
}