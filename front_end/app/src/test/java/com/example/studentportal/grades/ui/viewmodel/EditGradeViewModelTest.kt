package com.example.studentportal.grades.ui.viewmodel

import androidx.lifecycle.Observer
import com.example.studentportal.common.service.models.successFlow
import com.example.studentportal.common.usecase.DefaultError
import com.example.studentportal.common.usecase.failureFlow
import com.example.studentportal.course.ui.model.UserType
import com.example.studentportal.grades.ui.model.GradeUiModel
import com.example.studentportal.grades.usecase.EditGradeUseCase
import com.example.studentportal.grades.usecase.model.GradeUseCaseModel
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.unmockkConstructor
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class EditGradeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: EditGradeViewModel
    private lateinit var mockObserver: Observer<UiState>

    private val initialGradeUi = GradeUiModel(
        id = "1",
        score = 90,
        studentFirstName = "John",
        studentLastName = "Doe",
        studentId = "1",
        submissionLink = null
    )

    private val initialGrade = GradeUseCaseModel(
        id = "1",
        score = 90,
        studentFirstName = "John",
        studentLastName = "Doe",
        studentId = "1",
        submissionLink = null,
        assignmentId = null
    )

//    @Rule
//    @JvmField
//    var coroutineTestRule: CoroutineTestRule = CoroutineTestRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockkConstructor(EditGradeUseCase::class)
        viewModel = EditGradeViewModel(testDispatcher)
        mockObserver = mockk(relaxed = true)
        viewModel.uiResultLiveData.observeForever(mockObserver)
    }

    @After
    fun tearDown() {
        viewModel.uiResultLiveData.removeObserver(mockObserver)
        unmockkConstructor(EditGradeUseCase::class)
        stopKoin()
    }

    @Test
    fun testUpdateScore() {
        viewModel.updateScore("95")
        assertThat(viewModel.uiResultLiveData.value?.score).isEqualTo("95")
        verify { mockObserver.onChanged(UiState(score = "95")) }
    }

    @Test
    fun testUpdateSubmissionLink() {
        viewModel.updateSubmissionLink("https://submission.link")
        assertThat(viewModel.uiResultLiveData.value?.submissionLink).isEqualTo("https://submission.link")
        verify { mockObserver.onChanged(UiState(submissionLink = "https://submission.link")) }
    }

    @Test
    fun testUpdateText() {
        viewModel.updateText("Hello")
        assertThat(viewModel.uiResultLiveData.value?.text).isEqualTo("Hello")
        verify { mockObserver.onChanged(UiState(text = "Hello")) }
    }

    @Test
    fun `testOnButtonClick as student`() = runTest {
        val initialGrade = GradeUiModel(
            id = "1",
            score = 90,
            studentFirstName = "John",
            studentLastName = "Doe",
            studentId = "1",
            submissionLink = "https://submission.link"
        )
        val resultGrade = GradeUseCaseModel(
            id = "1",
            score = 90,
            studentFirstName = "John",
            studentLastName = "Doe",
            studentId = "1",
            submissionLink = "https://new_submission_link",
            assignmentId = null
        )

        val uiState = UiState(
            text = "https://new_submission_link",
            score = "90",
            submissionLink = "https://submission.link"
        )
        viewModel._uiResultLiveData.value = uiState

        coEvery { anyConstructed<EditGradeUseCase>().launch() } returns successFlow(
            resultGrade
        )

        viewModel.onButtonClick(initialGrade, UserType.STUDENT)
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.uiResultLiveData.value?.score).isEqualTo("90")
        assertThat(viewModel.uiResultLiveData.value?.submissionLink).isEqualTo("https://new_submission_link")
        assertThat(viewModel.uiResultLiveData.value?.text).isEqualTo("")
        verify { mockObserver.onChanged(UiState(score = "90", submissionLink = "https://new_submission_link", text = "")) }
    }

    @Test
    fun `testOnButtonClick as faculty`() = runTest {
        val initialGrade = GradeUiModel(
            id = "1",
            score = 90,
            studentFirstName = "John",
            studentLastName = "Doe",
            studentId = "1",
            submissionLink = "https://submission.link"
        )
        val resultGrade = GradeUseCaseModel(
            id = "1",
            score = 80,
            studentFirstName = "John",
            studentLastName = "Doe",
            studentId = "1",
            submissionLink = "https://submission.link",
            assignmentId = null
        )

        val uiState = UiState(
            text = "80",
            score = "90",
            submissionLink = "https://submission.link"
        )
        viewModel._uiResultLiveData.value = uiState

        coEvery { anyConstructed<EditGradeUseCase>().launch() } returns successFlow(
            resultGrade
        )

        viewModel.onButtonClick(initialGrade, UserType.FACULTY)
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.uiResultLiveData.value?.score).isEqualTo("80")
        assertThat(viewModel.uiResultLiveData.value?.submissionLink).isEqualTo("https://submission.link")
        assertThat(viewModel.uiResultLiveData.value?.text).isEqualTo("")
        verify { mockObserver.onChanged(UiState(score = "80", submissionLink = "https://submission.link", text = "")) }
    }

    @Test
    fun testUpdateObject_success() = runTest {
        val updatedGrade = GradeUiModel(
            id = "1",
            score = 95,
            studentFirstName = "John",
            studentLastName = "Doe",
            studentId = "1",
            submissionLink = "https://submission.link"
        )

        coEvery { anyConstructed<EditGradeUseCase>().launch() } returns successFlow(
            initialGrade
        )

        viewModel.updateObject(updatedGrade)
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.uiResultLiveData.value?.score).isEqualTo("95")
        assertThat(viewModel.uiResultLiveData.value?.submissionLink).isEqualTo("https://submission.link")
        assertThat(viewModel.uiResultLiveData.value?.text).isEqualTo("")
        verify { mockObserver.onChanged(UiState(score = "95", submissionLink = "https://submission.link", text = "")) }
    }

    @Test
    fun testUpdateObject_failure() = runTest {
        val updatedGrade = GradeUiModel(
            id = "1",
            score = 95,
            studentFirstName = "John",
            studentLastName = "Doe",
            studentId = "1",
            submissionLink = "https://submission.link"
        )

        coEvery { anyConstructed<EditGradeUseCase>().launch() } returns failureFlow(DefaultError("error"))

        val uiState = UiState(
            text = "80",
            score = "90",
            submissionLink = "https://submission.link"
        )
        viewModel._uiResultLiveData.value = uiState

        viewModel.updateObject(updatedGrade)
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.uiResultLiveData.value).isEqualTo(uiState)
        verify { mockObserver.onChanged(UiState.INITIAL) }
    }
}
