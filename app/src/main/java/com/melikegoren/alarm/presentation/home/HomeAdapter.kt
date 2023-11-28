package com.melikegoren.alarm.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.melikegoren.alarm.databinding.AlarmListItemBinding
import com.melikegoren.alarm.domain.model.AlarmModel
import com.melikegoren.alarm.util.toAlarmEntity

class HomeAdapter(
    private var alarmList: List<AlarmModel>,
    private val onHomeClickListener: OnHomeClickListener
) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    class HomeViewHolder(val binding: AlarmListItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding =
            AlarmListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun getItemCount(): Int =
        alarmList.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val alarm = alarmList[position]
        holder.binding.apply {
            tvTime.text = "${alarm.hour}:${alarm.minute}"
            tvDays.text = alarm.days.toString()

            btnDelete.setOnClickListener {
                onHomeClickListener.removeAlarm(alarm.toAlarmEntity())
            }
        }
    }


}
