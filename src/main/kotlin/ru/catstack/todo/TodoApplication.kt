package ru.catstack.todo

import ru.catstack.todo.engine.Application
import ru.catstack.todo.ui.MainScreen

class TodoApplication : Application() {
    override fun onCreate() {
        println("Application running...")
        setScreen(MainScreen())
    }
}