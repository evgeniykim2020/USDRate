package com.evgeniykim.usdrate.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.evgeniykim.usdrate.data.SharedPrefManager
import com.evgeniykim.usdrate.databinding.ItemDailyRateBinding
import com.evgeniykim.usdrate.model.USDModel
import java.text.SimpleDateFormat
import java.util.*

class USDAdapter : RecyclerView.Adapter<USDAdapter.MainViewHolder>() {

    private val ratesItem = mutableListOf<USDModel>()
    private val TAG = "USDAdapter"
    private val today = Calendar.getInstance()
    private val formatter = SimpleDateFormat("dd.MM.yyyy")

    fun submitItems(item: List<USDModel>) {
        ratesItem.addAll(item)
        notifyDataSetChanged()
    }

    inner class MainViewHolder(val binding: ItemDailyRateBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ItemDailyRateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val itemList = ratesItem[position]

        if (formatter.format(today.time).equals(itemList.Date)) {
            holder.binding.date.setTextColor(Color.RED)
            holder.binding.rate.setTextColor(Color.RED)
            holder.binding.date.text = itemList.Date
            holder.binding.rate.text = itemList.Value
            //SharedPrefManager.todayMarketRate = itemList.Value
        } else {
            holder.binding.date.text = itemList.Date
            holder.binding.rate.text = itemList.Value
        }

        Log.i(TAG, "Adapter data $itemList")
    }

    override fun getItemCount(): Int = ratesItem.size
}