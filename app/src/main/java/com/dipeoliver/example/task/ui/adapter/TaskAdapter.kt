package com.dipeoliver.example.task.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.dipeoliver.example.task.databinding.ItemAdapterBinding
import com.dipeoliver.example.task.model.Task

class TaskAdapter(
    private val taskList: List<Task>,
    val taskSelected: (Task, Int) -> Unit
) : RecyclerView.Adapter<TaskAdapter.MyViewHolder>() {

    companion object {
        val SELECT_BACK: Int = 1
        val SELECT_REMOVE: Int = 2
        val SELECT_EDIT: Int = 3
        val SELECT_DETAILS: Int = 4
        val SELECT_NEXT: Int = 5
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemAdapterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class MyViewHolder(val binding: ItemAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    //    mostrar o tamanho da lista
    override fun getItemCount() = taskList.size


    //    exibir as informações de cada tarefa
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val task = taskList[position]
        holder.binding.textDescription.text = task.description

        holder.binding.btnDelete.setOnClickListener {
            taskSelected(task, SELECT_REMOVE)
        }
        holder.binding.btnEdit.setOnClickListener {
            taskSelected(task, SELECT_EDIT)
        }
        holder.binding.btnEdit.setOnClickListener {
            taskSelected(task, SELECT_EDIT)
        }

//        mostra ou não as setas
        when (task.status) {
            0 -> {
                holder.binding.ibBack.isVisible = false
                holder.binding.ibNext.setOnClickListener {
                    taskSelected(
                        task,
                        SELECT_NEXT
                    )
                }
            }
            1 -> {

                holder.binding.ibBack.setOnClickListener {
                    taskSelected(
                        task,
                        SELECT_BACK
                    )
                }
                holder.binding.ibNext.setOnClickListener {
                    taskSelected(
                        task,
                        SELECT_NEXT
                    )
                }
            }
            else -> {
                holder.binding.ibNext.isVisible = false
                holder.binding.ibBack.setOnClickListener {
                    taskSelected(
                        task,
                        SELECT_BACK
                    )
                }
            }
        }
    }
}