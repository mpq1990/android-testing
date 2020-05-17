package com.example.android.architecture.blueprints.todoapp.taskdetail

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isChecked
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.ServiceLocator
import com.example.android.architecture.blueprints.todoapp.data.FakeAndroidTestRepository
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class TaskDetailFragmentTest {

    private lateinit var repository: TasksRepository

    @Before
    fun setUp() {
        repository = FakeAndroidTestRepository()
        ServiceLocator.tasksRepository = repository
    }

    @After
    fun clear() {
        runBlocking {
            ServiceLocator.resetRepository()
        }
    }

    @Test
    fun activeTaskDetails_DisplayedInUi() {
        runBlockingTest {
            val activeTask = Task("Active Task", "Active Task Details", false)
            repository.saveTask(activeTask)

            val bundle = TaskDetailFragmentArgs(activeTask.id).toBundle()
            launchFragmentInContainer<TaskDetailFragment>(bundle, R.style.AppTheme)

            onView(withId(R.id.task_detail_title_text)).check(matches(isDisplayed()))
            onView(withId(R.id.task_detail_title_text)).check(matches(withText("Active Task")))
            onView(withId(R.id.task_detail_description_text)).check(matches(isDisplayed()))
            onView(withId(R.id.task_detail_description_text)).check(matches(withText("Active Task Details")))
            onView(withId(R.id.task_detail_complete_checkbox)).check(matches(isDisplayed()))
            onView(withId(R.id.task_detail_complete_checkbox)).check(matches(not(isChecked())))
        }
    }

    @Test
    fun completedTaskDetails_DisplayedInUi() = runBlockingTest {
        // GIVEN - Add completed task to the DB
        val activeTask = Task("Active Task 2", "Active Task Details 2", true)
        repository.saveTask(activeTask)

        // WHEN - Details fragment launched to display task
        val bundle = TaskDetailFragmentArgs(activeTask.id).toBundle()
        launchFragmentInContainer<TaskDetailFragment>(bundle, R.style.AppTheme)

        // THEN - Task details are displayed on the screen
        // make sure that the title/description are both shown and correct
        onView(withId(R.id.task_detail_title_text)).check(matches(isDisplayed()))
        onView(withId(R.id.task_detail_title_text)).check(matches(withText("Active Task 2")))
        onView(withId(R.id.task_detail_description_text)).check(matches(isDisplayed()))
        onView(withId(R.id.task_detail_description_text)).check(matches(withText("Active Task Details 2")))
        onView(withId(R.id.task_detail_complete_checkbox)).check(matches(isDisplayed()))
        onView(withId(R.id.task_detail_complete_checkbox)).check(matches(isChecked()))
    }
}