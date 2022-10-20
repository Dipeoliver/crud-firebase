package com.dipeoliver.example.task.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dipeoliver.example.task.R
import com.dipeoliver.example.task.databinding.FragmentDoingBinding
import com.dipeoliver.example.task.databinding.FragmentDoneBinding
import com.dipeoliver.example.task.databinding.FragmentSplashBinding
import com.dipeoliver.example.task.databinding.FragmentTodoBinding


class DoneFragment : Fragment() {
    private var _binding: FragmentDoneBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDoneBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}