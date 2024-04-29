package com.example.studentportal.course.ui.activtiy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.FragmentActivity
import com.example.studentportal.R
import com.example.studentportal.common.ui.showBaseFragment
import com.example.studentportal.course.ui.fragment.CourseFragment
import com.example.studentportal.course.ui.model.UserType
import com.example.studentportal.databinding.ActivityCourseBinding
import com.example.studentportal.home.ui.model.BaseCourseUiModel

class CourseActivity : FragmentActivity() {
    @VisibleForTesting
    internal lateinit var binding: ActivityCourseBinding

    private val userType: UserType
        get() {
            val name = intent.getStringExtra(KEY_USER_TYPE)
            return UserType.valueOf(name.orEmpty())
        }

    private val userId: String
        get() {
            return intent.getStringExtra(KEY_USER_ID).orEmpty()
        }

    private val courseId: String
        get() {
            return intent.getStringExtra(KEY_COURSE_ID).orEmpty()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseBinding.inflate(layoutInflater).initUi()
        actionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.arrow_back)
        }
        supportFragmentManager.showBaseFragment(
            fragment = CourseFragment.newInstance(
                userType = userType,
                userId = userId,
                courseId = courseId
            ),
            addToBackStack = false,
            containerId = binding.flContent.id
        )
        setContentView(binding.root)
    }

    private fun ActivityCourseBinding.initUi(): ActivityCourseBinding {
        toolbar.setTitle(intent.getStringExtra(KEY_USER_COURSE_NAME))
        setActionBar(this.toolbar)
        return this
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val KEY_USER_ID = "KEY_USER_ID"
        const val KEY_USER_TYPE = "KEY_USER_TYPE"
        const val KEY_COURSE_ID = "KEY_COURSE_ID"
        const val KEY_USER_COURSE_NAME = "KEY_COURSE_NAME"
        fun intent(
            owner: Context,
            course: BaseCourseUiModel.CourseUiModel,
            userId: String,
            userType: String
        ): Intent {
            val intent = Intent(owner, CourseActivity::class.java)
            intent.putExtra(KEY_USER_ID, userId)
            intent.putExtra(KEY_USER_TYPE, userType)
            intent.putExtra(KEY_COURSE_ID, course.id)
            intent.putExtra(KEY_USER_COURSE_NAME, course.name)
            return intent
        }
    }
}
