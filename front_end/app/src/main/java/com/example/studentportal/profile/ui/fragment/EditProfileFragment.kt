package com.example.studentportal.profile.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.example.studentportal.common.ui.fragment.BaseDialogFragment
import com.example.studentportal.databinding.FragmentEditProfileBinding
import com.example.studentportal.profile.ui.layout.EditProfileLayout
import com.example.studentportal.profile.ui.model.UserUiModel
import com.example.studentportal.profile.ui.viewModel.EditProfileViewModel
import kotlinx.coroutines.Dispatchers

class EditProfileFragment : BaseDialogFragment<FragmentEditProfileBinding>(TAG) {
    internal val viewModel by viewModels<EditProfileViewModel> {
        EditProfileViewModel.ViewModelProviderFactory(
            dispatcher = Dispatchers.IO,
            userUiModel = existingUser
        )
    }

    private val existingUser: UserUiModel?
        get() = requireArguments().getParcelable<UserUiModel>(KEY_USER)

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEditProfileBinding {
        val binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        binding.composeView.setContent {
            EditProfileLayout(
                existingUser = existingUser,
                viewModel = viewModel,
                modifier = Modifier.fillMaxSize(),
                onCloseClicked = { dismiss() },
                onSubmitClicked = {
                    val initialData = existingUser ?: return@EditProfileLayout
                    val user = viewModel.uiResultLiveData.value?.toUiModel(
                        initialData.id,
                        initialData.password,
                        initialData.firstName,
                        initialData.lastName,
                        initialData.type
                    ) ?: return@EditProfileLayout

                    setFragmentResult(
                        KEY_FRAGMENT_RESULT,
                        bundleOf(
                            KEY_USER to user
                        )
                    )
                    dismiss()
                }
            )
        }
        return binding
    }

    companion object {
        const val TAG = "EDIT_PROFILE"
        const val KEY_USER = "KEY_USER"
        const val KEY_FRAGMENT_RESULT = "KEY_FRAGMENT_RESULT"

        fun newInstance(user: UserUiModel): EditProfileFragment {
            val fragment = EditProfileFragment()
            fragment.arguments = bundleOf(
                KEY_USER to user
            )
            return fragment
        }
    }
}
