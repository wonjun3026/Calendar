package com.android.calendar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.calendar.databinding.ItemWeekBinding
import com.android.calendar.databinding.ItemWeekNotBinding
import com.android.calendar.databinding.ItemWeekSelectBinding

class CalendarAdapter(
    private val dayList: MutableList<Int>,
    private val weekList: MutableList<String>,
    private val k: Int,
    private val monthDay: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    override fun getItemViewType(position: Int): Int {
        return when {
            position < k + 1 -> 0
            position > k + monthDay -> 0
            typeList[position] == 2 -> 2
            else -> 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> {
                val binding = ItemWeekNotBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                NotHolder(binding)
            }
            1 -> {
                val binding = ItemWeekBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                Holder(binding)
            }
            2 -> {
                val binding = ItemWeekSelectBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                SelectHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")

        }
    }

    override fun getItemCount(): Int {
        return dayList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position)
        }
        if (position < k + 1 || position > k + monthDay){
            val holder1 = holder as NotHolder
            holder1.day.text = dayList[position].toString()
            holder1.week.text = weekList[position]

        } else if (typeList[position] == 2) {
            val holder2 = holder as SelectHolder
            holder2.day.text = dayList[position].toString()
            holder2.week.text = weekList[position]
        } else {
            val holder3 = holder as Holder
            holder3.day.text = dayList[position].toString()
            holder3.week.text = weekList[position]
        }
    }

    inner class Holder(binding: ItemWeekBinding) : RecyclerView.ViewHolder(binding.root) {
        val day = binding.day
        val week = binding.week
    }

    inner class NotHolder(binding: ItemWeekNotBinding) : RecyclerView.ViewHolder(binding.root) {
        val day = binding.day
        val week = binding.week

    }

    inner class SelectHolder(binding: ItemWeekSelectBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val day = binding.day
        val week = binding.week
    }

    companion object {
        var typeList = MutableList(42) { 0 }
        var lastClick = 20
    }

}