package com.gleam.kiwi.view.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gleam.kiwi.R
import com.gleam.kiwi.model.Task

class TaskRecyclerAdapter(
    private val onItemCLick: (position: Int) -> Unit
) : RecyclerView.Adapter<TaskRecyclerViewHolder>() {

    private lateinit var recyclerView: RecyclerView
    private var taskList = emptyList<Task>()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onBindViewHolder(holder: TaskRecyclerViewHolder, position: Int) {
        holder.apply {
            titleTextView.text = taskList[position].title
        }
    }

    override fun getItemCount() = taskList.size

    fun setTasks(tasks: List<Task>) {
        this.taskList = tasks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskRecyclerViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.task_list_cell, parent, false)
                .apply {
                    setOnClickListener { view ->
                        onItemCLick(
                            recyclerView.getChildAdapterPosition(view)
                        )
                    }
                }

        return TaskRecyclerViewHolder(view)
    }


}