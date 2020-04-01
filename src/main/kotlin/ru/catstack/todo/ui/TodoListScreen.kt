package ru.catstack.todo.ui

import org.kodein.di.generic.instance
import ru.catstack.todo.di.kodein
import ru.catstack.todo.engine.BaseScreen

class TodoListScreen : BaseScreen() {
    private val viewModel: TodoListViewModel by kodein.instance()

    private var isRunning = true

    private val commands = hashMapOf<String, () -> Unit>(
        "help" to ::executeHelp,
        "exit" to ::executeExit
    )

    override fun onCreate() {
        println("Todo List:\n")

        viewModel.loadTodoList()

        printList()

        println("\nType 'Help' to view commands")

        while (isRunning) {
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
        print("Enter command:\n> ")
        val command = readLine()?.toLowerCase() ?: "";
        if (commands.containsKey(command))
            commands[command]?.invoke()
        else
            println("This command is not exists!")
    }

    private fun executeHelp() {
        println("Help text")
    }

    private fun executeExit() {
        isRunning = false
    }
}