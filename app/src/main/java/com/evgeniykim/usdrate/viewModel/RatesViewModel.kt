package com.evgeniykim.usdrate.viewModel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evgeniykim.usdrate.model.Base
import com.evgeniykim.usdrate.model.USDModel
import com.evgeniykim.usdrate.model.ValCurs
import com.evgeniykim.usdrate.network.ResponseResult
import com.evgeniykim.usdrate.network.RetrofitModule
import com.evgeniykim.usdrate.network.USDApi
import com.evgeniykim.usdrate.network.USDRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class RatesViewModel : ViewModel() {

    private val mService by lazy { USDRepo() }
    private val TAG = "RatesViewModel"
    private val formatter = SimpleDateFormat("dd/MM/yyyy")
    val flowData = MutableStateFlow<List<USDModel>>(listOf())

    @SuppressLint("SimpleDateFormat")
    fun getRates() {

        viewModelScope.launch {
            val response = mService.getUSDrates(
                date_req1 = getPrevMonth(),
                date_req2 = getToday(),
                VAL_NM_RQ = "R01235"
            )
            when (response.status) {
                ResponseResult.Status.SUCCESS -> {
                    Log.i(TAG, "Data ${response.data}")
                    response.data?.Record?.map {
                        flowData.emit(listOf(it))
                    }
                }
                ResponseResult.Status.ERROR -> {
                    Log.i(TAG, "Data ${response.error}")
                }
            }
            Log.i(TAG, "Rates list is ${response.data}")
        }
    }

    private fun getToday(): String {
        val calendar = Calendar.getInstance()
        return formatter.format(calendar.time)
    }

    private fun getPrevMonth(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -1)
        return formatter.format(calendar.time)
    }
}