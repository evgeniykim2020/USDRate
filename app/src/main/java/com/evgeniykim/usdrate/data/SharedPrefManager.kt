package com.evgeniykim.usdrate.data

import android.annotation.SuppressLint
import android.content.Context
import com.evgeniykim.usdrate.App

@SuppressLint("StaticFieldLeak")
object SharedPrefManager {
    private val context: Context = App.appContext
    private val sPref = context.getSharedPreferences("app_data", Context.MODE_PRIVATE)

    var handRate: String
        get() {
            return sPref.getString(
                "handRate",
                ""
            )!!
        }
        set(value) {
            val ed = sPref.edit()
            ed.putString("handRate", value)
            ed.apply()
        }

    var todayMarketRate: String
        get() {
            return sPref.getString(
                "todayMarketRate",
                ""
            )!!
        }
        set(value) {
            val ed = sPref.edit()
            ed.putString("todayMarketRate", value)
            ed.apply()
        }
}