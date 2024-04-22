package com.example.studentportal.profile.ui.fragment

//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.lifecycle.Observer
//import com.example.studentportal.profile.ui.fragment.Model.UserProfileModel
//import com.example.studentportal.profile.ui.fragment.ViewModel.User
//import com.example.studentportal.profile.ui.fragment.ViewModel.UserAPIService
//import com.example.studentportal.profile.ui.fragment.ViewModel.UserProfileViewModel
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.mockito.ArgumentCaptor
//import org.mockito.Captor
//import org.mockito.Mock
//import org.mockito.Mockito.`when`
//import org.mockito.Mockito.verify
//import org.mockito.MockitoAnnotations
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.studentportal.profile.ui.fragment.Model.UserProfileModel
import com.example.studentportal.profile.ui.fragment.ViewModel.User
import com.example.studentportal.profile.ui.fragment.ViewModel.UserAPIService
import com.example.studentportal.profile.ui.fragment.ViewModel.UserProfileViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserProfileViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: UserProfileViewModel

    @Mock
    private lateinit var userApi: UserAPIService

    @Mock
    private lateinit var userCall: Call<List<User>>

    @Mock
    private lateinit var apiResponseObserver: Observer<UserProfileModel>

    @Captor
    private lateinit var callbackCaptor: ArgumentCaptor<Callback<List<User>>>

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        `when`(userApi.getUsers()).thenReturn(userCall)
        viewModel = UserProfileViewModel(userApi).apply {
            userProfileModel.observeForever(apiResponseObserver)
        }
    }

    @Test
    fun `test loading data successfully`() {
        // Arrange
        val mockUsers = listOf(User("1", "password", "STUDENT", "John", "Doe", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", "john.doe@exampleuni.com", "(XXX) XXX XXXX"))
        `when`(userCall.enqueue(callbackCaptor.capture())).thenAnswer {
            val callback = callbackCaptor.value
            callback.onResponse(userCall, Response.success(mockUsers))
        }

        // Act
        viewModel.fetchUserData()

        // Assert
        verify(apiResponseObserver).onChanged(
            UserProfileModel(
                userName = "John Doe",
                userQualification = "MS. Software Engineering",
                userEmail = "john.doe@exampleuni.com",
                userPhone = "(XXX) XXX XXXX",
                userBiography = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                userLinks = emptyList()
            )
        )
    }

    @Test
    fun `test loading data with error`() {
        // Arrange
        `when`(userCall.enqueue(callbackCaptor.capture())).thenAnswer {
            val callback = callbackCaptor.value
            callback.onFailure(userCall, RuntimeException("Failed to load data"))
        }

        // Act
        viewModel.fetchUserData()

        // Assert
        verify(apiResponseObserver).onChanged(
            UserProfileModel(
                errorMessage = "Failed to load user data: Failed to load data"
            )
        )
    }
}
