package ru.catstack.todo.engine

abstract class Application {
    abstract fun onCreate()

    fun setScreen(screen: BaseScreen) {
        screen.application = this
        screen.onCreate()
    }
}