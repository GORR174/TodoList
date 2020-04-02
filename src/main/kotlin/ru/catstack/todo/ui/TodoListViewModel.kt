package ru.catstack.todo.ui

import ru.catstack.todo.data.TodoRepository
import ru.catstack.todo.model.Task
import java.lang.StringBuilder

class TodoListViewModel(private val repository: TodoRepository) {
    sealed class ResponseState {
        object Successful : ResponseState()
        object MissingArguments : ResponseState()
        data class IncorrectNumber(val input: String) : ResponseState()
        data class TaskIsNotExists(val taskIndex: Int) : ResponseState()
    }

    private var mutableTodoList = ArrayList<Task>()
    val todoList: List<Task>
        get() = mutableTodoList

    fun loadTodoList() {
        if (todoList.isEmpty())
            mutableTodoList = repository.loadTodoList()
    }

    fun addTask(args: List<String>): ResponseState {
        if (args.isNotEmpty()) {
            val textBuilder = StringBuilder()
            args.forEach { textBuilder.append("$it ") }
            textBuilder.deleteCharAt(textBuilder.lastIndex)
            val text = textBuilder.toString()

            addTask(text)

            return ResponseState.Successful
        } else {
            return ResponseState.MissingArguments
        }
    }

    private fun addTask(todoText: String) {
        mutableTodoList.add(Task(todoText, false))
        saveToRepository()
    }

    fun deleteTask(args: List<String>): ResponseState {
        if (args.isEmpty())
            return ResponseState.MissingArguments

        val taskNumber = args.first().toIntOrNull() ?: return ResponseState.IncorrectNumber(args.first())

        if (todoList.size >= taskNumber && taskNumber >= 1) {
            deleteTask(taskNumber - 1)
            return ResponseState.Successful
        } else {
            return ResponseState.TaskIsNotExists(taskNumber - 1)
        }
    }

    private fun deleteTask(taskIndex: Int) {
        mutableTodoList.removeAt(taskIndex)
        saveToRepository()
    }

    fun completeTask(args: List<String>): ResponseState {
        if (args.isEmpty())
            return ResponseState.MissingArguments

        val taskNumber = args.first().toIntOrNull() ?: return ResponseState.IncorrectNumber(args.first())

        if (todoList.size >= taskNumber && taskNumber >= 1) {
            completeTask(taskNumber - 1)
            return ResponseState.Successful
        } else {
            return ResponseState.TaskIsNotExists(taskNumber - 1)
        }
    }

    private fun completeTask(taskIndex: Int) {
        val task = mutableTodoList[taskIndex]
        task.isCompleted = !task.isCompleted
        saveToRepository()
    }

    private fun saveToRepository() {
        repository.saveTodoList(mutableTodoList)
    }
}