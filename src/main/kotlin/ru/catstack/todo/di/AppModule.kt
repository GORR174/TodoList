package ru.catstack.todo.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import ru.catstack.todo.data.JsonTodoRepository
import ru.catstack.todo.data.TestTodoRepository
import ru.catstack.todo.ui.TodoListScreen
import ru.catstack.todo.ui.TodoListViewModel

val appModule = Kodein.Module("app") {
    bind<TodoListScreen>() with provider { TodoListScreen() }
    bind<TodoListViewModel>() with singleton { TodoListViewModel(instance<JsonTodoRepository>()) }

    bind<TestTodoRepository>() with singleton { TestTodoRepository() }
    bind<JsonTodoRepository>() with singleton { JsonTodoRepository(instance()) }

    bind<Gson>() with singleton {
        GsonBuilder()
            .setPrettyPrinting()
            .create()
    }
}