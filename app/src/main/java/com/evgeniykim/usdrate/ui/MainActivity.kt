package com.evgeniykim.usdrate.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.evgeniykim.usdrate.adapter.USDAdapter
import com.evgeniykim.usdrate.data.SharedPrefManager
import com.evgeniykim.usdrate.databinding.ActivityMainBinding
import com.evgeniykim.usdrate.viewModel.RatesViewModel
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var fixedRate: Double? = null
    private val viewModel: RatesViewModel by viewModels()
    private lateinit var adapter: USDAdapter
    private val TAG = "MainActivity"
    private val currentDay = SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().time)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setHandRate()
        viewModel.getRates()
        initViewModel()
        setupAdapter()
        setupAlert()
    }

    private fun setHandRate() {

        if (!SharedPrefManager.handRate.isNullOrEmpty()) {
            binding.fixedRateText.text = SharedPrefManager.handRate
        }

        binding.setRateButton.setOnClickListener {
            fixedRate = if (binding.editRateText.text.isNullOrEmpty()) {
                0.0
            } else {
                binding.editRateText.text.toString().replace(",", ".").toDouble()
            }
            binding.handRate.clearFocus()
            binding.editRateText.setText("")
            SharedPrefManager.handRate = fixedRate.toString()
            binding.fixedRateText.text = SharedPrefManager.handRate

            setupAlert()
        }
    }

    private fun initViewModel() {
        lifecycleScope.launchWhenStarted {
            viewModel.flowData.collect {
                data -> adapter.submitItems(data)
                Log.i(TAG, "Data $data")
                data.map {
                    if (currentDay.equals(it.Date)) {
                        SharedPrefManager.todayMarketRate = it.Value
                        setupAlert()
                    }
                }
            }
        }
    }

    private fun setupAdapter() {
        adapter = USDAdapter()
        binding.ratesRecyclerview.adapter = adapter
        binding.ratesRecyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        Log.i(TAG, "Today market rate ${SharedPrefManager.todayMarketRate}")
    }

    private fun setupAlert() {
        val alert = AlarmDialog()
        if (!SharedPrefManager.todayMarketRate.isNullOrEmpty() && !SharedPrefManager.handRate.isNullOrEmpty()) {
            if (SharedPrefManager.todayMarketRate.replace(",", ".").toDouble() >
                SharedPrefManager.handRate.replace(",", ".").toDouble()) {
                alert.show(supportFragmentManager, "AlertDialog")
            }
        }
    }
}