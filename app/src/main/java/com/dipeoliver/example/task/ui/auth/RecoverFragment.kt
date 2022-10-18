package com.dipeoliver.example.task.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.dipeoliver.example.task.R
import com.dipeoliver.example.task.databinding.FragmentLoginBinding
import com.dipeoliver.example.task.databinding.FragmentRecoverBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RecoverFragment : Fragment() {
    private var _binding: FragmentRecoverBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recover, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        initClicks()
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
                "O campos e-mail não pode estar vazio",
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
                        "O link foi enviado com sucesso, verifique sua caixa de e-mail",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                binding.progressBar3.isVisible = false
            }
    }
}