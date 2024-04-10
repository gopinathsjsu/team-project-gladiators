package com.example.studentportal.home.ui.activity

import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.ActionProvider
import android.view.ContextMenu
import android.view.MenuItem
import android.view.SubMenu
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.IntegerRes
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.studentportal.common.ui.model.BaseUiState
import com.example.studentportal.common.usecase.DefaultError
import com.example.studentportal.home.ui.fragment.HomeFragment
import com.example.studentportal.home.ui.model.UserType
import com.example.studentportal.home.ui.model.UserUiModel
import com.example.studentportal.R
import com.example.studentportal.common.ui.MockDrawerState
import com.example.studentportal.common.ui.MockMenuItem
import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
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
        ActivityScenario.launch(HomeActivity::class.java)
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
        ActivityScenario.launch(HomeActivity::class.java)
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
