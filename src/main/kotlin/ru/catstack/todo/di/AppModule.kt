package ru.catstack.todo.di

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import ru.catstack.todo.data.TestTodoRepository
import ru.catstack.todo.ui.TodoListScreen
import ru.catstack.todo.ui.TodoListViewModel

val appModule = Kodein.Module("app") {
    bind<TodoListScreen>() with provider { TodoListScreen() }
    bind<TodoListViewModel>() with singleton { TodoListViewModel(instance()) }

    bind<TestTodoRepository>() with singleton { TestTodoRepository() }
}