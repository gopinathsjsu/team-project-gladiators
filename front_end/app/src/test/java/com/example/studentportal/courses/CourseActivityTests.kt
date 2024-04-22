package com.example.studentportal.courses

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CourseActivityTests {

    @Test
    fun activityLaunchesSuccessfully() {
        ActivityScenario.launch(CourseActivity::class.java).use { scenario ->
            scenario.moveToState(Lifecycle.State.CREATED)
            Assert.assertTrue(scenario.state.isAtLeast(Lifecycle.State.CREATED))
        }
    }
}
