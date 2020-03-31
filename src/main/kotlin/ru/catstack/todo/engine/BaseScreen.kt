package ru.catstack.todo.engine

abstract class BaseScreen {
    var application: Application? = null

    abstract fun onCreate()

    fun setScreen(screen: BaseScreen) {
        screen.application = application
        application?.setScreen(screen)
    }
}
