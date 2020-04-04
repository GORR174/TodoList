package ru.catstack.todo

import com.google.gson.Gson
import org.junit.Assert
import org.junit.Test
import org.kodein.di.generic.instance
import ru.catstack.todo.data.JsonTodoRepository
import ru.catstack.todo.di.kodein
import ru.catstack.todo.model.Task
import ru.catstack.todo.model.TodoResponse
import java.io.File

class JsonRepositoryTest {
    private val gson: Gson by kodein.instance()
    private val filePath = "test.json"
    private val repository = JsonTodoRepository(gson, filePath)

    @Test
    fun testEmptyFile() {
        val file = File(filePath)
        if (file.exists())
            file.delete()

        Assert.assertEquals(arrayListOf<Task>(), repository.loadTodoList())
    }

    @Test
    fun testSaving() {
        val saveList = arrayListOf(
            Task("Test1", false),
            Task("Test2", true)
        )
        repository.saveTodoList(saveList)
        val expectedResponse = TodoResponse(saveList.size, saveList)
        val file = File(filePath)
        val actualResponse = gson.fromJson(file.readText(), TodoResponse::class.java)
        Assert.assertEquals(expectedResponse, actualResponse)
    }

    @Test
    fun testEmptySaving() {
        val saveList = arrayListOf<Task>()
        repository.saveTodoList(saveList)
        val expectedResponse = TodoResponse(saveList.size, arrayListOf())
        val file = File(filePath)
        val actualResponse = gson.fromJson(file.readText(), TodoResponse::class.java)
        Assert.assertEquals(expectedResponse, actualResponse)
    }

    @Test
    fun testLoading() {
        val saveList = arrayListOf(
            Task("Test1", false),
            Task("Test2", true)
        )
        repository.saveTodoList(saveList)
        Assert.assertEquals(saveList, repository.loadTodoList())
    }
}