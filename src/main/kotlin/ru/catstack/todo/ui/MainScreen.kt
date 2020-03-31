package ru.catstack.todo.ui

import ru.catstack.todo.engine.BaseScreen

class MainScreen : BaseScreen() {
    override fun onCreate() {
        println("${this.javaClass.simpleName} created!")
    }
}