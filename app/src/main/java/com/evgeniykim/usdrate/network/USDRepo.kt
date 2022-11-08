package com.evgeniykim.usdrate.network

import com.evgeniykim.usdrate.model.Base
import com.evgeniykim.usdrate.model.USDModel
import com.evgeniykim.usdrate.model.ValCurs

class USDRepo : BaseDataSource() {

    private val mService by lazy { RetrofitModule.retrofit().create(USDApi::class.java) }

    suspend fun getUSDrates(date_req1: String, date_req2: String, VAL_NM_RQ: String) = getResult {
        mService.getUSD(date_req1, date_req2, VAL_NM_RQ)
    }

//    suspend fun getUSDrates(date_req1: String, date_req2: String, VAL_NM_RQ: String) {
//
//    }
}