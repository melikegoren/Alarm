package com.melikegoren.alarm.presentation.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import com.melikegoren.alarm.R
import com.melikegoren.alarm.databinding.FragmentHomeBinding
import com.melikegoren.alarm.domain.entity.AlarmEntity
import com.melikegoren.alarm.domain.model.AlarmModel
import com.melikegoren.alarm.presentation.viewModels.HomeViewModel
import com.melikegoren.alarm.util.AlarmHelper
import com.melikegoren.alarm.util.convertTimeToMilliseconds
import com.melikegoren.alarm.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), OnHomeClickListener {

    private var _binding: FragmentHomeBinding? = null
    val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var homeAdapter: HomeAdapter

    @Inject
    lateinit var alarmHelper: AlarmHelper

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Log.i("Permission: ", "Granted")
            } else {
                Log.i("Permission: ", "Denied")
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        binding.timePicker.setIs24HourView(true)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addAlarmtoDb()
        listAlarms()
        requestPermission()


    }

    private fun addAlarmtoDb() {

        binding.apply {
            cbMon.toggleCheckbox(getString(R.string.monday))
            cbTue.toggleCheckbox(getString(R.string.tuesday))
            cbWed.toggleCheckbox(getString(R.string.wednesday))
            cbThu.toggleCheckbox(getString(R.string.thursday))
            cbFri.toggleCheckbox(getString(R.string.friday))
            cbSat.toggleCheckbox(getString(R.string.saturday))
            cbSun.toggleCheckbox(getString(R.string.sunday))
        }

        viewModel.daysToAdd.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.btnSave.isEnabled = false
            } else {
                binding.btnSave.isEnabled = true
                val days = it
                binding.btnSave.setOnClickListener {
                    var hour = binding.timePicker.hour.toString()
                    var minute = binding.timePicker.minute.toString()

                    if(minute.length == 1 || hour.length == 1){
                        hour = ("0$hour")
                        minute = ("0$minute")
                    }

                    val alarmEntity =
                        AlarmEntity(hour = hour, minute = minute, days = days)

                    val timeInMilli = convertTimeToMilliseconds(hour.toInt(), minute.toInt())
                    alarmHelper.setAlarm(requireContext(), timeInMilli, days)

                    viewModel.addAlarm(alarmEntity)

                    uncheckCheckboxes()

                    lifecycleScope.launch {
                        delay(100L)
                        viewModel.getAllAlarms()
                    }
                }
            }
        }
    }

    private fun listAlarms() {
        viewModel.alarmList.observe(viewLifecycleOwner) {
            when (it) {
                is HomeUiState.Loading -> binding.progressBar.visibility = View.INVISIBLE
                is HomeUiState.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    requireContext().showToast(it.message)
                }

                is HomeUiState.Success -> {
                    if (it.data.size == 0) {
                        binding.tvNoAlarm.visibility = View.VISIBLE
                        binding.rvAlarms.adapter = null
                    } else {
                        binding.tvNoAlarm.visibility = View.INVISIBLE
                        val sortedList = it.data.sortedBy { it.hour }
                        setAdapter(binding.rvAlarms, sortedList)
                    }

                }
            }
        }
    }


    private fun setAdapter(recyclerView: RecyclerView, list: List<AlarmModel>) {
        homeAdapter = HomeAdapter(list, this)
        val layoutManager = LinearLayoutManager(this.requireContext())
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = homeAdapter
    }

    private fun MaterialCheckBox.toggleCheckbox(text: String) {
        this.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.toggleCheckbox(text, isChecked)
        }
    }

    private fun uncheckCheckboxes() {
        binding.apply {
            cbMon.isChecked = false
            cbTue.isChecked = false
            cbWed.isChecked = false
            cbThu.isChecked = false
            cbFri.isChecked = false
            cbSat.isChecked = false
            cbSun.isChecked = false
        }
    }

    override fun removeAlarm(alarmEntity: AlarmEntity) {
        viewModel.deleteAlarm(alarmEntity)
        lifecycleScope.launch {
            delay(100L)
            viewModel.getAllAlarms()
        }
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestPermission() {

        requestPermissionLauncher.launch(
            Manifest.permission.POST_NOTIFICATIONS
        )

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.POST_NOTIFICATIONS
            )
            binding.tvPermission.visibility = View.VISIBLE
        } else if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            binding.tvPermission.visibility = View.GONE
        }

    }




    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

interface OnHomeClickListener {
    fun removeAlarm(alarmEntity: AlarmEntity)
}