package ru.catstack.todo.ui

import org.kodein.di.generic.instance
import ru.catstack.todo.di.kodein
import ru.catstack.todo.engine.BaseScreen

class TodoListScreen : BaseScreen() {
    private val viewModel: TodoListViewModel by kodein.instance()

    override fun onCreate() {
        println("Todo List:\n")

        viewModel.loadTodoList()

        printList()

        println("\nType 'Help' to view commands")

        while (true) {
            enterCommand()
        }
    }

    private fun printList() {
        if (viewModel.todoList.isEmpty()) {
            println("List is empty. Type 'add' to create new task")
        } else {
            viewModel.todoList.forEachIndexed { index, task ->
                println("[${index + 1}] [${if (task.isCompleted) "V" else "X"}] - ${task.taskText}")
            }
        }
    }

    private fun enterCommand() {
        while (true) {
            print("Enter command:\n> ")
            readLine()
        }
    }
}