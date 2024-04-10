package com.example.studentportal.common.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding>(val fragmentTag: String) : Fragment() {

    private var _binding: T? = null
    val binding
        get() = _binding ?: throw IllegalAccessException("View has been destroyed")

    abstract fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): T

    @IdRes
    public abstract fun menuItem(): Int
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflateBinding(inflater, container)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
