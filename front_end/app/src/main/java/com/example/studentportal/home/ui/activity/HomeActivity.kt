package com.example.studentportal.home.ui.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.annotation.IdRes
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentActivity
import com.example.studentportal.R
import com.example.studentportal.auth.ui.showLogoutDialog
import com.example.studentportal.common.di.clearAuthenticatedUserData
import com.example.studentportal.common.di.koin
import com.example.studentportal.common.ui.fragment.BaseFragment
import com.example.studentportal.common.ui.popBackStackToFragment
import com.example.studentportal.common.ui.showBaseFragment
import com.example.studentportal.course.ui.model.UserType
import com.example.studentportal.databinding.ActivityHomeBinding
import com.example.studentportal.home.ui.fragment.HomeFragment
import com.example.studentportal.notifications.ui.fragment.NotificationsFragment
import com.example.studentportal.profile.ui.fragment.ProfileFragment
import com.google.android.material.navigation.NavigationView

class HomeActivity : FragmentActivity(), NavigationView.OnNavigationItemSelectedListener {

    @VisibleForTesting
    internal lateinit var binding: ActivityHomeBinding
    private lateinit var actionBarToggle: ActionBarDrawerToggle

    val userId: String
        get() = intent.getStringExtra(KEY_USER_ID).orEmpty()

    val userType: UserType
        get() = UserType.valueOf(intent.getStringExtra(KEY_USER_TYPE).orEmpty())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater).initUI()
        // Set up activity action bar
        actionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.drawer_menu)
        }
        selectDrawerItem(R.id.nav_courses, false)
        setContentView(binding.root)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        selectDrawerItem(item.itemId, true)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                binding.drawerLayout.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun selectDrawerItem(
        @IdRes itemId: Int,
        addToBackStack: Boolean
    ) {
        val displayedFragment = supportFragmentManager.findFragmentById(binding.flContent.id) as? BaseFragment<*>
        if (displayedFragment?.menuItem() == itemId) {
            binding.drawerLayout.closeDrawers()
            return // Don't display fragment twice
        }
        val fragment = when (itemId) {
            R.id.nav_courses -> HomeFragment.newInstance(
                userId = userId,
                userType = userType
            )
            R.id.nav_profile -> ProfileFragment.newInstance(userId)
            R.id.nav_notifications -> NotificationsFragment.newInstance()
            else -> HomeFragment.newInstance(
                userId = userId,
                userType = userType
            )
        }
        val existingFragment = supportFragmentManager.findFragmentByTag(fragment.fragmentTag)
        if (existingFragment != null) {
            supportFragmentManager.popBackStackToFragment(fragment)
            binding.drawerLayout.closeDrawers()
            return // Go back in history to previous fragment
        }
        supportFragmentManager.showBaseFragment(
            fragment = fragment,
            addToBackStack = addToBackStack,
            containerId = binding.flContent.id
        )
        binding.drawerLayout.closeDrawers()
    }

    private fun ActivityHomeBinding.initUI(): ActivityHomeBinding {
        drawerLayout.apply {
            actionBarToggle = ActionBarDrawerToggle(
                this@HomeActivity,
                this,
                R.string.nav_open,
                R.string.nav_close
            )
            addDrawerListener(actionBarToggle)
            actionBarToggle.syncState()
        }
        setActionBar(homeToolbar)
        navigationView.setNavigationItemSelectedListener(this@HomeActivity)
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (supportFragmentManager.backStackEntryCount <= 0) {
                    showLogoutDialog(this@HomeActivity) {
                        koin.get<SharedPreferences>().clearAuthenticatedUserData() // Clear JwtToken
                        finish()
                    }
                }
            }
        })
        return this
    }

    companion object {
        const val KEY_USER_ID = "KEY_USER_ID"
        const val KEY_USER_TYPE = "KEY_USER_TYPE"
        fun intent(
            owner: Context,
            userId: String,
            userType: String
        ): Intent {
            val intent = Intent(owner, HomeActivity::class.java)
            intent.putExtra(KEY_USER_ID, userId)
            intent.putExtra(KEY_USER_TYPE, userType)
            return intent
        }
    }
}
