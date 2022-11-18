package com.dipeoliver.example.task.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.dipeoliver.example.task.R
import com.dipeoliver.example.task.databinding.FragmentDoingBinding
import com.dipeoliver.example.task.databinding.FragmentDoneBinding
import com.dipeoliver.example.task.databinding.FragmentSplashBinding
import com.dipeoliver.example.task.helper.FirebaseHelper
import com.dipeoliver.example.task.model.Task
import com.dipeoliver.example.task.ui.adapter.TaskAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class DoingFragment : Fragment() {
    private var _binding: FragmentDoingBinding? = null
    private val binding get() = _binding!!
    private lateinit var taskAdapter: TaskAdapter
    private val tasklist = mutableListOf<Task>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDoingBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTasks()
    }

    private fun getTasks() {
        FirebaseHelper
            .getDatabase()
            .child("task")
            .child(FirebaseHelper.getIdUser() ?: "")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        tasklist.clear()
                        for (snap in snapshot.children) {
                            val task = snap.getValue(Task::class.java) as Task
                            if (task.status == 1) tasklist.add(task)
                        }
                        binding.textinfo.text = ""
                        tasklist.reverse() // trazer a lista do mais novo para o antigo
                        initAdapter()
                    } else {
                        binding.textinfo.text = "Nehuma Tarefa encontrada"

                    }
                    binding.progressBar4.isVisible = false
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }

            })
    }

    private fun initAdapter() {
        binding.rvTaskDoing.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTaskDoing.setHasFixedSize(true)
        taskAdapter = TaskAdapter(requireContext(), tasklist) { task, select ->
            optionSelect(task, select)
        }
        binding.rvTaskDoing.adapter = taskAdapter
    }

    private fun optionSelect(task: Task, select: Int) {
        when (select) {
            TaskAdapter.SELECT_REMOVE -> {
                deleteTask(task)
            }

        }
    }

    private fun deleteTask(task: Task) {
        FirebaseHelper
            .getDatabase()
            .child("task")
            .child(FirebaseHelper.getIdUser() ?: "")
            .child(task.id)
            .removeValue()

//        tasklist.remove(task)
//        taskAdapter.notifyDataSetChanged()
    }
}