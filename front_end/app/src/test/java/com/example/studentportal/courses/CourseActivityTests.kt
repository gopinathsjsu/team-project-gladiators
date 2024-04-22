package com.example.studentportal.courses

import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin

@RunWith(AndroidJUnit4::class)
class CourseActivityTests {
    @After
    fun tearDown() {
        stopKoin()
    }
    @Test
    fun activityLaunchesSuccessfully() {
        val intent = Intent(ApplicationProvider.getApplicationContext(), CourseActivity::class.java)
        intent.action = Intent.ACTION_MAIN
        intent.addCategory(Intent.CATEGORY_LAUNCHER)

        ActivityScenario.launch<CourseActivity>(intent).use { scenario ->
            assertThat(scenario.state).isEqualTo(Lifecycle.State.RESUMED)
        }
    }
}
