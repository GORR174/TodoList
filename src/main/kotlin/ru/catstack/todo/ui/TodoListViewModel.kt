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

    fun addTodo(todoText: String) {
        mutableTodoList.add(Task(todoText, false))
        repository.saveTodoList(mutableTodoList)
    }
}