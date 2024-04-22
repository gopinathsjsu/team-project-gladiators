package com.example.studentportal.courses

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import androidx.fragment.app.FragmentContainerView
import com.example.studentportal.profile.ui.fragment.ProfileFragment

class CourseActivity : AppCompatActivity() {

    companion object {
        private var currentViewId = 1
        fun generateViewId(): Int {
            return currentViewId++
        }
    }

    private lateinit var fragmentContainer: FragmentContainerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create the LinearLayout for the whole activity layout
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        }

        // Define a container to host fragments, which will show the ProfileFragment
        fragmentContainer = FragmentContainerView(this).apply {
            id = generateViewId()
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
         1f  // This gives it weight to use available space
            )
        }
        layout.addView(fragmentContainer)

        // List of users as an example
        val users = listOf("User 1", "User 2", "User 3")
        users.forEach { userName ->
            val userTextView = TextView(this).apply {
                text = userName
                textSize = 20f
                setPadding(24) // Padding for better touch area
                setOnClickListener {
                    showProfileFragment()
                }
            }
            layout.addView(userTextView)
        }

        // Set the LinearLayout as the main content view of the activity
        setContentView(layout)
    }

    private fun showProfileFragment() {
        // Navigate to the ProfileFragment when a user TextView is clicked
        val profileFragment = ProfileFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(fragmentContainer.id, profileFragment)
            .addToBackStack(null)
            .commit()
    }
}
