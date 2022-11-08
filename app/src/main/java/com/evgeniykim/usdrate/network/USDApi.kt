package com.evgeniykim.usdrate.network

import com.evgeniykim.usdrate.model.Base
import com.evgeniykim.usdrate.model.USDModel
import com.evgeniykim.usdrate.model.ValCurs
import org.simpleframework.xml.Path
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface USDApi {
//    @GET("/XML_dynamic.asp")
//    suspend fun getUSD(
//        @Query("ValCurs") ValCurs: ValCurs
//    ) : Response<USDModel>

//    @GET("XML_dynamic.asp")
//    suspend fun getUSD(
//        @Query("date_req1") date_req1: String,
//        @Query("date_req2") date_req2: String,
//        @Query("VAL_NM_RQ") VAL_NM_RQ: String,
//    ): ValCurs

    @GET("XML_dynamic.asp")
    suspend fun getUSD(
        @Query("date_req1") date_req1: String,
        @Query("date_req2") date_req2: String,
        @Query("VAL_NM_RQ") VAL_NM_RQ: String,
    ): Response<ValCurs>
}