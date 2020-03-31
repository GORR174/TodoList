package ru.catstack.todo.di

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider
import ru.catstack.todo.ui.MainScreen

val appModule = Kodein.Module("app") {
    bind<MainScreen>() with provider { MainScreen() }
}