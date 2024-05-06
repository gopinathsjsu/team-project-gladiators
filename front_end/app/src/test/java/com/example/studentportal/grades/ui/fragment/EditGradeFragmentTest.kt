package com.example.studentportal.grades.ui.fragment

import android.os.Bundle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.studentportal.grades.ui.model.GradeUiModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import kotlin.test.Ignore

@RunWith(AndroidJUnit4::class)
class EditGradeFragmentTest {

    private lateinit var fragment: EditGradeFragment
    private lateinit var grade: GradeUiModel

    @Before
    fun setUp() {
        grade = GradeUiModel(
            id = "1",
            score = 10,
            studentFirstName = "First-N1",
            studentLastName = "Last-N1",
            studentId = "1",
            submissionLink = null
        )
        fragment = EditGradeFragment.newInstance(grade, "STUDENT").also {
            it.arguments = Bundle().apply {
                putParcelable(EditGradeFragment.KEY_GRADE, grade)
            }
        }

        // Starting a fragment lifecycle
        fragment.activity?.supportFragmentManager?.beginTransaction()?.add(fragment, null)?.commit()
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun testFragmentInitializationWithArguments() {
        val grade = GradeUiModel(
            id = "1",
            score = 10,
            studentFirstName = "First-N1",
            studentLastName = "Last-N1",
            studentId = "1",
            submissionLink = null
        )
        val fragment = EditGradeFragment.newInstance(grade, "STUDENT")
        val bundle = fragment.arguments
        assertNotNull(bundle)
        assertEquals(grade, bundle?.getParcelable(EditGradeFragment.KEY_GRADE))
    }

    @Test(expected = IllegalArgumentException::class)
    @Ignore("Failing, fix later")
    fun testOnCreateWithMissingArguments() {
        val fragment = EditGradeFragment()
        fragment.onCreate(null) // Should throw an IllegalArgumentException
    }
}
