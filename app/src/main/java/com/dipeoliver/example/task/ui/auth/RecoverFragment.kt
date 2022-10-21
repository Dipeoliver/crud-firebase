package com.dipeoliver.example.task.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dipeoliver.example.task.R
import com.dipeoliver.example.task.databinding.FragmentLoginBinding
import com.dipeoliver.example.task.databinding.FragmentRecoverBinding
import com.dipeoliver.example.task.helper.FirebaseHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RecoverFragment : Fragment() {
    private var _binding: FragmentRecoverBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var receivedArgs: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecoverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        initClicks()

        receivedArgs = requireArguments().get("email").toString()
        binding.edtEmail.setText(receivedArgs)
    }

    private fun initClicks() {
        binding.btnEnviar.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        val email = binding.edtEmail.text.toString().trim()

        if (email.isNotEmpty()) {
            binding.progressBar3.isVisible = true
            recoveryUser(email)
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.empty_email),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun recoveryUser(email: String) {

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.sent_email),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        FirebaseHelper.validError(task.exception?.message ?: ""),
                        Toast.LENGTH_LONG
                    ).show()
                }
                binding.progressBar3.isVisible = false

            }
    }
}