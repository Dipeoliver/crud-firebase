package com.dipeoliver.example.task.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.dipeoliver.example.task.R
import com.dipeoliver.example.task.databinding.FragmentFormTaskBinding
import com.dipeoliver.example.task.databinding.FragmentTodoBinding
import com.dipeoliver.example.task.helper.FirebaseHelper
import com.dipeoliver.example.task.model.Task


class FormTaskFragment : Fragment() {

    private var _binding: FragmentFormTaskBinding? = null
    private val binding get() = _binding!!

    private lateinit var task: Task
    private var newTask: Boolean = true

    private var statusTask: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFormTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        binding.btnSave.setOnClickListener { validateData() }
        binding.rbGroup.setOnCheckedChangeListener() { _, id ->
            statusTask = when (id) {
                R.id.rb_todo -> 0
                R.id.rb_doing -> 1
                else -> 2
            }
        }
    }

    private fun validateData() {
        binding.btnSave.setOnClickListener {
            val description = binding.edtDescription.text.toString().trim()
            if (description.isNotEmpty()) {
                binding.progressBar5.isVisible = true

                if (newTask) task = Task()
                task.description = description
                task.status = statusTask

                saveTask()
            } else {

                Toast.makeText(
                    requireContext(),
                    "Informe uma descrição para a Tarefa",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun saveTask() {
        FirebaseHelper
            .getDatabase()
            .child("task")
            .child(FirebaseHelper.getIdUser() ?: "") // id do usuario
            .child(task.id)
            .setValue(task)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (newTask) { // nova tarefa

                        findNavController().popBackStack() // voltar para a tela anterior
                        Toast.makeText(
                            requireContext(),
                            "Tarefa Salva com sucesso",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else { // iditando tarefa
                        binding.progressBar5.isVisible = false
                        Toast.makeText(
                            requireContext(),
                            "Tarefa Atualizada com Sucesso",
                            Toast.LENGTH_SHORT
                        )
                    }

                } else {
                    Toast.makeText(requireContext(), "Erro ao salvar Tarefa", Toast.LENGTH_SHORT)
                        .show()

                }
            }.addOnFailureListener {
                binding.progressBar5.isVisible = false
                Toast.makeText(requireContext(), "Erro ao salvar Tarefa", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}