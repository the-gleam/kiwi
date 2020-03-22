package com.gleam.kiwi.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.gleam.kiwi.R
import com.gleam.kiwi.databinding.DayDetailFragmentBinding
import com.gleam.kiwi.view.recycler.TaskRecyclerAdapter
import com.gleam.kiwi.viewModel.DayDetailViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class DayDetailFragment : Fragment() {
   // private val dayDetailViewModel: DayDetailViewModel by viewModel()
    private lateinit var dayDetailViewModel: DayDetailViewModel
    private lateinit var dayDetailFragmentBinding: DayDetailFragmentBinding
    private val date = "2020/03/21" //placeholder
    private lateinit var taskRecyclerAdapter: TaskRecyclerAdapter

    private fun testFragment(){
        Log.i("DayDetailFragment", "call testFragment()")
        dayDetailViewModel = DayDetailViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("DayDetailFragment", "call onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dayDetailFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.day_detail_fragment, container, false)
        dayDetailFragmentBinding.lifecycleOwner = this

        taskRecyclerAdapter = TaskRecyclerAdapter(this@DayDetailFragment::onItemClick)

        dayDetailFragmentBinding.taskRecyclerView.apply{
            adapter = taskRecyclerAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        return dayDetailFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun onItemClick(click: View, position: Int){
        //TODO:implement viewModel.onItemClick
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        testFragment()
        observeViewModel(dayDetailViewModel)
    }

    private fun observeViewModel(viewModel: DayDetailViewModel) {
        viewModel.taskList?.observe(viewLifecycleOwner, Observer { tasks ->
                tasks?.also {
                    Log.i("DayDetailFragment", dayDetailViewModel.getDayTasks(tasks, date).toString())
                    taskRecyclerAdapter.setTasks(dayDetailViewModel.getDayTasks(tasks, date))
                }
            }
        )
    }

}
