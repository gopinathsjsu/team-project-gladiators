package com.example.studentportal.common.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding

abstract class BaseDialogFragment<T : ViewBinding>(val fragmentTag: String) : DialogFragment() {

    private var _binding: T? = null
    val binding
        get() = _binding ?: throw IllegalAccessException("View has been destroyed")

    abstract fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflateBinding(inflater, container)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val params = dialog?.window?.attributes ?: return
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog?.window?.setAttributes(params)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
