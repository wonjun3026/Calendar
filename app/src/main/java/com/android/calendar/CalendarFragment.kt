package com.android.calendar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.PagerSnapHelper
import com.android.calendar.adapter.CalendarAdapter
import com.android.calendar.adapter.CalendarAdapter.Companion.lastClick
import com.android.calendar.adapter.CalendarAdapter.Companion.typeList
import com.android.calendar.adapter.ScheduleAdapter
import com.android.calendar.databinding.FragmentCalendarBinding
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale


class CalendarFragment : Fragment(), CalendarAdapter.ItemClick, ScheduleAdapter.ItemClick {

    private var _binding: FragmentCalendarBinding? = null
    private val binding: FragmentCalendarBinding
        get() = _binding!!

    private lateinit var calendarAdapter: CalendarAdapter
    private lateinit var scheduleAdapter: ScheduleAdapter
    private var today: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

        binding.textView.setOnClickListener {
            if (binding.textView.text == "주간 일정 펼치기"){
                binding.todayButton.visibility = View.VISIBLE
                binding.recyclerviewSchedule.visibility = View.VISIBLE
                binding.textView.text = "주간 일정 접기"
            } else{
                binding.todayButton.visibility = View.INVISIBLE
                binding.recyclerviewSchedule.visibility = View.INVISIBLE
                binding.textView.text = "주간 일정 펼치기"
            }
        }

        binding.todayButton.setOnClickListener {
            binding.recyclerviewList.smoothScrollToPosition(today)
            typeList[today] = 2
            calendarAdapter.notifyDataSetChanged()
            lastClick = today
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {
        val (date, day) = getCurrentDate()
        val dayList = mutableListOf<Int>()
        val weekList = mutableListOf<String>()
        val weekCal = listOf<String>("일", "월", "화", "수", "목", "금", "토", "일", "월", "화", "수", "목", "금", "토") // 요일
        val dateCal = mutableListOf<Int>(31, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31) // 전월
        today = 0

        val currentYear = date.toString().dropLast(6).toInt() // 년도
        val currentMonth = date.toString().drop(5).dropLast(3).toInt() // 월
        val currentDate = date.toString().drop(8).toInt() // 날짜
        val currentDay = day.dropLast(2)  // 요일
        var k = 0

        if (currentYear % 4 == 0){
            dateCal[2] = 29
        }

        binding.month.text = "${currentMonth}월"


        while (weekCal[k] != currentDay){
            k++
        }
        for (i in 1..6){
            weekList.add("일")
            weekList.add("월")
            weekList.add("화")
            weekList.add("수")
            weekList.add("목")
            weekList.add("금")
            weekList.add("토")
        }

        for (l in dateCal[currentMonth-1]-k..dateCal[currentMonth-1]){
            dayList.add(l)
        }
        for (j in 1..dateCal[currentMonth]){
            dayList.add(j)
            typeList[j+k] = 1
        }
        for (m in 1..<42-dateCal[currentMonth]-k){
            dayList.add(m)
        }
        calendarAdapter = CalendarAdapter(dayList, weekList, k, dateCal[currentMonth])
        binding.recyclerviewList.apply {
            adapter = calendarAdapter
        }
        calendarAdapter.itemClick = this

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerviewList)

        scheduleAdapter = ScheduleAdapter()
        binding.recyclerviewSchedule.apply {
            adapter = scheduleAdapter
        }

        scheduleAdapter.itemClick = this

        today = k + currentDate

        binding.recyclerviewList.layoutManager?.scrollToPosition(today)
        typeList[today] = 2
        calendarAdapter.notifyDataSetChanged()
        lastClick = today
    }

    private fun getCurrentDate(): Pair<LocalDate, String> {
        val today = LocalDate.now()
        val day = today.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
        return Pair(today, day)
    }

    override fun onClick(view: View, position: Int) {
        if ( typeList[position] == 1){
            typeList[lastClick] = 1
            typeList[position] = 2
        }
        lastClick = position
        calendarAdapter.notifyDataSetChanged()
    }

    override fun onClick1(view: View, position: Int) {
        TODO("Not yet implemented")
    }
}