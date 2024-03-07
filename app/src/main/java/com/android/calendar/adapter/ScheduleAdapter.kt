package com.android.calendar.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.calendar.databinding.ItemScheduleBinding

class ScheduleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val timeList = listOf<String>( "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23")

    interface ItemClick {
        fun onClick1(view : View, position : Int)
    }

    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val scheduleHolder = holder as Holder

        scheduleHolder.itemView.setOnClickListener {
            itemClick?.onClick1(it, position)
        }
        scheduleHolder.time.text = timeList[position]
    }

    override fun getItemCount(): Int {
        return timeList.size
    }


    inner class Holder(val binding: ItemScheduleBinding) : RecyclerView.ViewHolder(binding.root){
        val time = binding.time
    }
}