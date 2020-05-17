package com.example.android.architecture.blueprints.todoapp.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.android.architecture.blueprints.todoapp.data.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class TasksDaoTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ToDoDatabase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(), ToDoDatabase::class.java
        ).build()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun insertTaskAndGetById() = runBlockingTest {
        // GIVEN - Insert a task.
        val task = Task("title", "description")
        database.taskDao().insertTask(task)

        // WHEN - Get the task by id from the database.
        val loaded = database.taskDao().getTaskById(task.id)

        // THEN - The loaded data contains the expected values.
        assertThat(loaded as Task, notNullValue())
        assertThat(loaded.id, `is`(task.id))
        assertThat(loaded.title, `is`(task.title))
        assertThat(loaded.description, `is`(task.description))
        assertThat(loaded.isCompleted, `is`(task.isCompleted))
    }

    @Test
    fun updateTaskAndGetById() {
        runBlockingTest {
            // 1. Insert a task into the DAO.
            val task = Task("title 2", "description 2")
            database.taskDao().insertTask(task)

            val loaded = database.taskDao().getTaskById(task.id)
            assertThat(loaded as Task, notNullValue())
            assertThat(loaded.id, `is`(task.id))
            assertThat(loaded.title, `is`(task.title))
            assertThat(loaded.description, `is`(task.description))
            assertThat(loaded.isCompleted, `is`(task.isCompleted))

            // 2. Update the task by creating a new task with the same ID but different attributes.
            val updatedTask = Task("yolo", "yolo description", id = task.id)
            database.taskDao().updateTask(updatedTask)

            // 3. Check that when you get the task by its ID, it has the updated values.
            val reLoaded = database.taskDao().getTaskById(task.id)
            assertThat(reLoaded as Task, notNullValue())
            assertThat(reLoaded.id, `is`(task.id))
            assertThat(reLoaded.title, `is`(updatedTask.title))
            assertThat(reLoaded.description, `is`(updatedTask.description))
            assertThat(reLoaded.isCompleted, `is`(updatedTask.isCompleted))
        }
    }
}