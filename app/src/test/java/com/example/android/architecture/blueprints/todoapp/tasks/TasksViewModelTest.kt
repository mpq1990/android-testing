package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TasksViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var tasksViewModel: TasksViewModel

    @Before
    fun setUp() {
        tasksViewModel = TasksViewModel(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun addNewTask_setsNewTaskEvent() {

        tasksViewModel.addNewTask()

        val value = tasksViewModel.newTaskEvent.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled(), (not(nullValue())))
//        val observer = Observer<Event<Unit>> {}
//        try {
//            // Observe the LiveData forever
//            tasksViewModel.newTaskEvent.observeForever(observer)
//
//            // When adding a new task
//            tasksViewModel.addNewTask()
//
//            // Then the new task event is triggered
//            val value = tasksViewModel.newTaskEvent.value
//            assertThat(value?.getContentIfNotHandled(), (not(nullValue())))
//        } finally {
//            // Whatever happens, don't forget to remove the observer!
//            tasksViewModel.newTaskEvent.removeObserver(observer)
//        }

        // Then the new task event is triggered
    }

    @Test
    fun setFilterAllTasks_tasksAddViewVisible() {
        // When
        tasksViewModel.setFiltering(TasksFilterType.ALL_TASKS)

        // Then
        assertThat(tasksViewModel.tasksAddViewVisible.getOrAwaitValue(), `is`(true))
    }
}