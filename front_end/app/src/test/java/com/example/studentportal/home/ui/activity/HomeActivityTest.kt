package com.example.studentportal.home.ui.activity

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.studentportal.R
import com.example.studentportal.common.ui.MockDrawerState
import com.example.studentportal.common.ui.MockMenuItem
import com.example.studentportal.course.ui.model.UserType
import com.example.studentportal.home.ui.fragment.HomeFragment
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `test initial setup`() {
        ActivityScenario.launch<HomeActivity>(
            HomeActivity.intent(
                ApplicationProvider.getApplicationContext(),
                "userId",
                UserType.FACULTY.name
            )
        )
            .use { scenario ->
                scenario.onActivity { homeActivity ->
                    val fragmentManager = homeActivity.supportFragmentManager

                    // Verify homeFragment is displayed
                    assertThat(
                        fragmentManager.findFragmentByTag(HomeFragment.TAG)
                    ).isNotNull()

                    // Verify backstack is empty
                    assertThat(fragmentManager.backStackEntryCount).isEqualTo(0)
                }
            }
    }

    @Test
    fun `test navigation`() {
        val mockDrawerState = MockDrawerState()
        ActivityScenario.launch<HomeActivity>(
            HomeActivity.intent(
                ApplicationProvider.getApplicationContext(),
                "userId",
                UserType.FACULTY.name
            )
        )
            .use { scenario ->
                scenario.onActivity { homeActivity ->
                    homeActivity.onOptionsItemSelected(MockMenuItem(0)) // Consume invalid selection

                    val fragmentManager = homeActivity.supportFragmentManager
                    homeActivity.binding.drawerLayout.addDrawerListener(mockDrawerState)
                    assertThat(mockDrawerState.state).isEqualTo(MockDrawerState.State.CLOSED)

                    // Open Drawer
                    homeActivity.onOptionsItemSelected(MockMenuItem(android.R.id.home))
                    homeActivity.onOptionsItemSelected(MockMenuItem(0)) // Consume invalid selection
                    assertThat(mockDrawerState.state).isEqualTo(MockDrawerState.State.OPEN)

                    // Try opening the same menu item
                    homeActivity.onNavigationItemSelected(MockMenuItem(R.id.nav_courses))
                    assertThat(fragmentManager.backStackEntryCount).isEqualTo(0)
                    assertThat(mockDrawerState.state).isEqualTo(MockDrawerState.State.CLOSED)

                    // Open Drawer Second time
                    homeActivity.onOptionsItemSelected(MockMenuItem(android.R.id.home))
                    assertThat(mockDrawerState.state).isEqualTo(MockDrawerState.State.OPEN)

                    // Go to notifications
                    homeActivity.onNavigationItemSelected(MockMenuItem(R.id.nav_notifications))
                    fragmentManager.executePendingTransactions()
                    assertThat(fragmentManager.backStackEntryCount).isEqualTo(1)
                    assertThat(mockDrawerState.state).isEqualTo(MockDrawerState.State.CLOSED)

                    // Open Drawer Third Time
                    homeActivity.onOptionsItemSelected(MockMenuItem(android.R.id.home))
                    assertThat(mockDrawerState.state).isEqualTo(MockDrawerState.State.OPEN)

                    // Go to Profile
                    homeActivity.onNavigationItemSelected(MockMenuItem(R.id.nav_profile))
                    fragmentManager.executePendingTransactions()
                    assertThat(fragmentManager.backStackEntryCount).isEqualTo(2)
                    assertThat(mockDrawerState.state).isEqualTo(MockDrawerState.State.CLOSED)

                    // Open Drawer 4th Time
                    homeActivity.onOptionsItemSelected(MockMenuItem(android.R.id.home))
                    assertThat(mockDrawerState.state).isEqualTo(MockDrawerState.State.OPEN)

                    // Go to Notifications
                    homeActivity.onNavigationItemSelected(MockMenuItem(R.id.nav_notifications))
                    fragmentManager.executePendingTransactions()
                    assertThat(fragmentManager.backStackEntryCount).isEqualTo(1)
                    assertThat(mockDrawerState.state).isEqualTo(MockDrawerState.State.CLOSED)

                    // Open Drawer 5th Time
                    homeActivity.onOptionsItemSelected(MockMenuItem(android.R.id.home))
                    assertThat(mockDrawerState.state).isEqualTo(MockDrawerState.State.OPEN)

                    // Go back to courses
                    homeActivity.onNavigationItemSelected(MockMenuItem(R.id.nav_courses))
                    fragmentManager.executePendingTransactions()
                    assertThat(fragmentManager.backStackEntryCount).isEqualTo(0)
                    assertThat(mockDrawerState.state).isEqualTo(MockDrawerState.State.CLOSED)
                }
            }
    }
}
