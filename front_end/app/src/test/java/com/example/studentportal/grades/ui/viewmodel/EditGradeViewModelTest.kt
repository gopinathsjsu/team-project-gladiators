//
// import com.example.studentportal.MainDispatcherTestRule
// import com.example.studentportal.common.service.models.successFlow
// import com.example.studentportal.course.ui.model.UserType
// import com.example.studentportal.grades.ui.model.GradeUiModel
// import com.example.studentportal.grades.ui.viewmodel.EditGradeViewModel
// import com.example.studentportal.grades.usecase.EditGradeUseCase
// import com.example.studentportal.grades.usecase.model.GradeUseCaseModel
// import io.mockk.coEvery
// import io.mockk.mockkConstructor
// import io.mockk.unmockkConstructor
// import kotlinx.coroutines.ExperimentalCoroutinesApi
// import kotlinx.coroutines.test.StandardTestDispatcher
// import kotlinx.coroutines.test.advanceUntilIdle
// import kotlinx.coroutines.test.runBlockingTest
// import org.junit.After
// import org.junit.Before
// import org.junit.Rule
// import org.junit.Test
// import org.koin.core.context.stopKoin
// import kotlin.test.assertEquals
//
// @ExperimentalCoroutinesApi
// class EditGradeViewModelTest {
//    private val mainDispatcher = StandardTestDispatcher()
//    private lateinit var viewModel: EditGradeViewModel
//    val initialGrade = GradeUiModel(
//        id = "1",
//        score = 10,
//        studentFirstName = "First-N1",
//        studentLastName = "Last-N1",
//        studentId = "1",
//        submissionLink = null,
//    )
//    @get:Rule
//    var instantExecutorRule = InstantTaskExecutorRule()
//
//    @get:Rule
//    var mainDispatcherRule = MainDispatcherTestRule(mainDispatcher)
//
//    @Before
//    fun before() {
//        mockkConstructor(EditGradeUseCase::class)
//        viewModel = EditGradeViewModel(mainDispatcher)
//    }
//
//    @After
//    fun tearDown() {
//        unmockkConstructor(EditGradeUseCase::class)
//        stopKoin()
//    }
//
//    @Test
//    fun `updateText updates text state`() {
//        viewModel.updateText("new text")
//        assertEquals("new text", viewModel.uiResultLiveData.value?.text)
//    }
//
//    @Test
//    fun `onButtonClick updates the submission link for STUDENT userType`() = runBlockingTest {
//        val useCaseModel = GradeUseCaseModel(
//            id = "1",
//            score = 10,
//            studentFirstName = "First-N1",
//            studentLastName = "Last-N1",
//            studentId = "1",
//            submissionLink = null,
//            assignmentId = "1"
//        )
//        val newText = "newText"
//        viewModel.updateText(newText)
//        coEvery { anyConstructed<EditGradeUseCase>().launch() } returns successFlow(
//            useCaseModel
//        )
//
//        viewModel.onButtonClick(
//            initialGrade = initialGrade,
//            userType = UserType.STUDENT
//        )
//        advanceUntilIdle() // Ensure all coroutines are completed
//
//        assertEquals(newText, viewModel.uiResultLiveData.value?.text)
//    }
//
//    @Test
//    fun `onButtonClick updates the score for non-STUDENT userType`() = runBlockingTest {
//        val useCaseModel = GradeUseCaseModel(
//            id = "1",
//            score = 10,
//            studentFirstName = "First-N1",
//            studentLastName = "Last-N1",
//            studentId = "1",
//            submissionLink = null,
//            assignmentId = "1"
//        )
//        val newScore = "92"
//        viewModel.updateScore(newScore)
//        coEvery { anyConstructed<EditGradeUseCase>().launch() } returns successFlow(
//            useCaseModel
//        )
//        viewModel.onButtonClick(
//            initialGrade = initialGrade,
//            userType = UserType.FACULTY
//        )
//        advanceUntilIdle() // Ensure all coroutines are completed
//
//        assertEquals(92, viewModel.uiResultLiveData.value?.score?.toInt())
//    }
// }
