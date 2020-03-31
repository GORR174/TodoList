package ru.catstack.todo

import org.kodein.di.generic.instance
import ru.catstack.todo.di.kodeinDirect
import ru.catstack.todo.engine.Application
import ru.catstack.todo.ui.MainScreen

class TodoApplication : Application() {
    override fun onCreate() {
        println("Application running...")
        setScreen(kodeinDirect.instance<MainScreen>())
    }
}