package com.example.android.architecture.blueprints.todoapp.statistics

import com.example.android.architecture.blueprints.todoapp.data.Task
import org.hamcrest.Matchers.`is`
import org.junit.Assert.*
import org.junit.Test

class StatisticsUtilsTest {

    @Test
    fun getActiveAndCompletedStats_noCompleted_returnsHundredZero() {
        // Given
        val tasks = listOf(Task("title", "description", false))

        // When
        val results = getActiveAndCompletedStats(tasks)

        // Then
        assertThat(results.activeTasksPercent, `is`(100f))
        assertThat(results.completedTasksPercent, `is`(0f))
    }
}