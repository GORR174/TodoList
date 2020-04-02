package ru.catstack.todo.ui

import org.kodein.di.generic.instance
import ru.catstack.todo.di.kodein
import ru.catstack.todo.engine.BaseScreen
import ru.catstack.todo.model.Command
import java.lang.StringBuilder

class TodoListScreen : BaseScreen() {
    private val viewModel: TodoListViewModel by kodein.instance()

    private var isRunning = true

    private val commands = arrayListOf(
        Command("help", "shows the help text", ::executeHelp),
        Command("add", "add new task. Usage: 'add message'", ::executeAdd),
        Command("delete", "deletes task by number. Usage: 'delete 1'", ::executeDelete),
        Command("print", "prints all tasks", ::printList),
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
        if (args.isNotEmpty()) {
            val textBuilder = StringBuilder()
            args.forEach { textBuilder.append("$it ") }
            textBuilder.deleteCharAt(textBuilder.lastIndex)
            val text = textBuilder.toString()

            viewModel.addTask(text)

            println("Successful")
        } else {
            println("You must add message to task. Example: 'add message'")
        }
    }

    private fun executeDelete(args: List<String>) {
        if (args.isEmpty()) {
            println("You must add task number. Example: 'delete 1'")
            return
        }

        val taskNumber = args.first().toIntOrNull()
        if (taskNumber == null) {
            println("Incorrect number")
            return
        }

        if (viewModel.todoList.size >= taskNumber && taskNumber >= 1) {
            viewModel.deleteTask(taskNumber - 1)
            println("Successful")
        } else {
            println("Task with number $taskNumber doesn't exists")
        }
    }

    private fun executeExit(args: List<String>) {
        isRunning = false
    }
}