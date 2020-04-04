package ru.catstack.todo.data

import com.google.gson.Gson
import ru.catstack.todo.model.Task
import ru.catstack.todo.model.TodoResponse
import java.io.File
import java.lang.Exception

class JsonTodoRepository(private val gson: Gson, private val filePath: String = "todo-list.json") : TodoRepository {
    override fun loadTodoList(): ArrayList<Task> {
        val file = File(filePath)
        if (!file.exists())
            return ArrayList()

        try {
            val response = gson.fromJson(file.readText(), TodoResponse::class.java)
            return response.tasks;
        } catch (e: Exception) {
            return ArrayList()
        }
    }

    override fun saveTodoList(todoList: ArrayList<Task>) {
        val response = TodoResponse(todoList.size, todoList)

        val file = File(filePath)
        if (!file.exists())
            file.createNewFile()

        file.writeText(gson.toJson(response))
    }
}