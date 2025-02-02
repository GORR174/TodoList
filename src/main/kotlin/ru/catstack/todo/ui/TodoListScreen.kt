package ru.catstack.todo.ui

import org.kodein.di.generic.instance
import ru.catstack.todo.di.kodein
import ru.catstack.todo.engine.BaseScreen
import ru.catstack.todo.model.Command
import ru.catstack.todo.ui.TodoListViewModel.ResponseState

class TodoListScreen : BaseScreen() {
    private val viewModel: TodoListViewModel by kodein.instance()

    private var isRunning = true

    private val commands = arrayListOf(
        Command("help", "shows the help text", ::executeHelp),
        Command("add", "add new task. Usage: 'add message'", ::executeAdd),
        Command("delete", "deletes task by number. Usage: 'delete 1'", ::executeDelete),
        Command("delete-all", "deletes all tasks", ::executeDeleteAll),
        Command("complete", "switch complete value to selected task. Usage: 'complete 1'", ::executeComplete),
        Command("up", "Moves up the selected task. Usage: 'up 2'", ::executeMoveUp),
        Command("down", "Moves down the selected task. Usage: 'down 1'", ::executeMoveDown),
        Command("print", "prints all tasks", ::printList),
        Command("filtered", "prints uncompleted tasks", ::printFilteredList),
        Command("exit", "close the application", ::executeExit)
    ).associateBy { it.name }

    override fun onCreate() {
        println("Todo List:\n")

        viewModel.loadTodoList()

        printList()

        println("\nType 'Help' to view commands")

        while (isRunning) {
            enterCommand()
        }
    }

    private fun printList(args: List<String>) {
        if (viewModel.todoList.isEmpty()) {
            println("List is empty. Type 'add message' to create new task")
        } else {
            viewModel.todoList.forEachIndexed { index, task ->
                println("[${index + 1}] [${if (task.isCompleted) "V" else "X"}] - ${task.taskText}")
            }
        }
    }

    private fun printFilteredList(args: List<String>) {
        if (viewModel.todoList.isEmpty()) {
            println("List is empty. Type 'add message' to create new task")
            return
        }

        val completedTasks = viewModel.todoList
            .associateBy { viewModel.todoList.indexOf(it) + 1 }
            .filterValues { !it.isCompleted }

        if (completedTasks.isEmpty()) {
            println("Congratulations. You have completed all tasks")
            return
        }

        completedTasks.forEach { (key, value) ->
            println("[$key] [X] - ${value.taskText}")
        }
    }

    private fun printList() {
        printList(listOf())
    }

    private fun enterCommand() {
        print("\nEnter command:\n> ")
        val input = readLine() ?: "";
        val separatedInput = input.split(' ');

        val command = separatedInput.first().toLowerCase()
        val args = separatedInput.drop(1)

        if (commands.containsKey(command))
            commands[command]?.function?.invoke(args)
        else
            println("This command is not exists!")
    }

    private fun executeHelp(args: List<String>) {
        println()
        commands.values.forEach {
            println("${it.name} - ${it.helpText}")
        }
    }

    private fun executeAdd(args: List<String>) {
        when (viewModel.addTask(args)) {
            ResponseState.Successful ->
                println("Successful")
            ResponseState.MissingArguments ->
                println("You must add message to task. Example: 'add message'")
            else -> println("Unexpected error")
        }
    }

    private fun executeDelete(args: List<String>) {
        when (val response = viewModel.deleteTask(args)) {
            ResponseState.MissingArguments ->
                println("You must add task number. Example: 'delete 1'")
            is ResponseState.IncorrectNumber ->
                println("Incorrect number")
            ResponseState.Successful ->
                println("Successful")
            is ResponseState.TaskIsNotExists ->
                println("Task with number ${response.taskIndex + 1} doesn't exists")
        }
    }

    private fun executeDeleteAll(args: List<String>) {
        when (viewModel.deleteAllTasks()) {
            ResponseState.Successful ->
                println("Successful")
            else -> println("Unexpected error")
        }
    }

    private fun executeComplete(args: List<String>) {
        when (val response = viewModel.completeTask(args)) {
            ResponseState.MissingArguments ->
                println("You must add task number. Example: 'complete 1'")
            is ResponseState.IncorrectNumber ->
                println("Incorrect number")
            ResponseState.Successful ->
                println("Successful")
            is ResponseState.TaskIsNotExists ->
                println("Task with number ${response.taskIndex + 1} doesn't exists")
        }
    }

    private fun executeMoveUp(args: List<String>) {
        when (val response = viewModel.moveUp(args)) {
            ResponseState.MissingArguments ->
                println("You must add task number. Example: 'up 2'")
            is ResponseState.IncorrectNumber ->
                println("Incorrect number")
            ResponseState.Successful -> {
                println("Successful")
                printList()
            }
            is ResponseState.TaskIsNotExists ->
                println("Task with number ${response.taskIndex + 1} doesn't exists")
        }
    }

    private fun executeMoveDown(args: List<String>) {
        when (val response = viewModel.moveDown(args)) {
            ResponseState.MissingArguments ->
                println("You must add task number. Example: 'down 1'")
            is ResponseState.IncorrectNumber ->
                println("Incorrect number")
            ResponseState.Successful -> {
                println("Successful")
                printList()
            }
            is ResponseState.TaskIsNotExists ->
                println("Task with number ${response.taskIndex + 1} doesn't exists")
        }
    }

    private fun executeExit(args: List<String>) {
        isRunning = false
    }
}