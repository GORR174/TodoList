package ru.catstack.todo

import com.google.gson.Gson
import org.junit.Assert
import org.junit.Test
import org.kodein.di.generic.instance
import ru.catstack.todo.data.JsonTodoRepository
import ru.catstack.todo.di.kodein
import ru.catstack.todo.model.Task
import ru.catstack.todo.ui.TodoListViewModel
import ru.catstack.todo.ui.TodoListViewModel.ResponseState

class TodoListViewModelTest {
    private val gson: Gson by kodein.instance()
    private val viewModel = TodoListViewModel(JsonTodoRepository(gson, "test.json"))

    private fun init() {
        viewModel.deleteAllTasks()
    }

    @Test
    fun addTaskTest() {
        init()
        Assert.assertEquals(ResponseState.Successful, viewModel.addTask(arrayListOf("Add", "task")))
        Assert.assertEquals(ResponseState.Successful, viewModel.addTask(arrayListOf("Add another task")))
        Assert.assertEquals(ResponseState.MissingArguments, viewModel.addTask(arrayListOf()))
        Assert.assertEquals(
            arrayListOf(
                Task("Add task", false),
                Task("Add another task", false)
            ),
            viewModel.todoList
        )
    }

    @Test
    fun completeTaskTest() {
        init()
        Assert.assertEquals(ResponseState.TaskIsNotExists(0), viewModel.completeTask(arrayListOf("1")))

        viewModel.addTask(arrayListOf("1"))
        viewModel.addTask(arrayListOf("2"))
        viewModel.addTask(arrayListOf("3"))

        Assert.assertEquals(ResponseState.MissingArguments, viewModel.completeTask(arrayListOf()))

        testIncorrectNumbers(viewModel::completeTask)

        Assert.assertEquals(ResponseState.TaskIsNotExists(-1), viewModel.completeTask(arrayListOf("0")))
        Assert.assertEquals(ResponseState.TaskIsNotExists(3), viewModel.completeTask(arrayListOf("4")))

        Assert.assertEquals(ResponseState.Successful, viewModel.completeTask(arrayListOf("2")))

        Assert.assertEquals(
            arrayListOf(
                Task("1", false),
                Task("2", true),
                Task("3", false)
            ),
            viewModel.todoList
        )
    }

    @Test
    fun deleteTaskTest() {
        init()
        Assert.assertEquals(ResponseState.TaskIsNotExists(0), viewModel.deleteTask(arrayListOf("1")))

        viewModel.addTask(arrayListOf("1"))
        viewModel.addTask(arrayListOf("2"))
        viewModel.addTask(arrayListOf("3"))

        Assert.assertEquals(ResponseState.MissingArguments, viewModel.deleteTask(arrayListOf()))

        testIncorrectNumbers(viewModel::completeTask)

        Assert.assertEquals(ResponseState.TaskIsNotExists(-1), viewModel.deleteTask(arrayListOf("0")))
        Assert.assertEquals(ResponseState.TaskIsNotExists(3), viewModel.deleteTask(arrayListOf("4")))

        Assert.assertEquals(ResponseState.Successful, viewModel.deleteTask(arrayListOf("2")))

        Assert.assertEquals(
            arrayListOf(
                Task("1", false),
                Task("3", false)
            ),
            viewModel.todoList
        )
    }

    @Test
    fun deleteAllTasksTest() {
        init()
        viewModel.addTask(arrayListOf("1"))
        viewModel.addTask(arrayListOf("2"))
        viewModel.addTask(arrayListOf("3"))
        Assert.assertEquals(3, viewModel.todoList.size)

        Assert.assertEquals(ResponseState.Successful, viewModel.deleteAllTasks())

        Assert.assertTrue(viewModel.todoList.isEmpty())
    }

    @Test
    fun moveUpTest() {
        init()
        Assert.assertEquals(ResponseState.TaskIsNotExists(0), viewModel.moveUp(arrayListOf("1")))

        viewModel.addTask(arrayListOf("1"))
        viewModel.addTask(arrayListOf("2"))
        viewModel.addTask(arrayListOf("3"))

        Assert.assertEquals(ResponseState.MissingArguments, viewModel.moveUp(arrayListOf()))

        testIncorrectNumbers(viewModel::completeTask)

        Assert.assertEquals(ResponseState.TaskIsNotExists(-1), viewModel.moveUp(arrayListOf("0")))
        Assert.assertEquals(ResponseState.TaskIsNotExists(3), viewModel.moveUp(arrayListOf("4")))

        Assert.assertEquals(ResponseState.Successful, viewModel.moveUp(arrayListOf("1")))

        Assert.assertEquals(
            arrayListOf(
                Task("1", false),
                Task("2", false),
                Task("3", false)
            ),
            viewModel.todoList
        )

        Assert.assertEquals(ResponseState.Successful, viewModel.moveUp(arrayListOf("2")))

        Assert.assertEquals(
            arrayListOf(
                Task("2", false),
                Task("1", false),
                Task("3", false)
            ),
            viewModel.todoList
        )
    }

    @Test
    fun moveDownTest() {
        init()
        Assert.assertEquals(ResponseState.TaskIsNotExists(0), viewModel.moveDown(arrayListOf("1")))

        viewModel.addTask(arrayListOf("1"))
        viewModel.addTask(arrayListOf("2"))
        viewModel.addTask(arrayListOf("3"))

        Assert.assertEquals(ResponseState.MissingArguments, viewModel.moveDown(arrayListOf()))

        testIncorrectNumbers(viewModel::completeTask)

        Assert.assertEquals(ResponseState.TaskIsNotExists(-1), viewModel.moveDown(arrayListOf("0")))
        Assert.assertEquals(ResponseState.TaskIsNotExists(3), viewModel.moveDown(arrayListOf("4")))

        Assert.assertEquals(ResponseState.Successful, viewModel.moveDown(arrayListOf("3")))

        Assert.assertEquals(
            arrayListOf(
                Task("1", false),
                Task("2", false),
                Task("3", false)
            ),
            viewModel.todoList
        )

        Assert.assertEquals(ResponseState.Successful, viewModel.moveDown(arrayListOf("2")))

        Assert.assertEquals(
            arrayListOf(
                Task("1", false),
                Task("3", false),
                Task("2", false)
            ),
            viewModel.todoList
        )
    }

    private fun testIncorrectNumbers(action: (ArrayList<String>) -> ResponseState) {
        Assert.assertEquals(ResponseState.IncorrectNumber("a"), action(arrayListOf("a")))
        Assert.assertEquals(ResponseState.IncorrectNumber("1.3"), action(arrayListOf("1.3")))
        Assert.assertEquals(ResponseState.IncorrectNumber("1,3"), action(arrayListOf("1,3")))
        Assert.assertEquals(ResponseState.IncorrectNumber("13d"), action(arrayListOf("13d")))
    }
}