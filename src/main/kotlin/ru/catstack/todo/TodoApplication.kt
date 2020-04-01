package ru.catstack.todo

import org.kodein.di.generic.instance
import ru.catstack.todo.di.kodeinDirect
import ru.catstack.todo.engine.Application
import ru.catstack.todo.ui.TodoListScreen

class TodoApplication : Application() {
    override fun onCreate() {
        setScreen(kodeinDirect.instance<TodoListScreen>())
    }
}