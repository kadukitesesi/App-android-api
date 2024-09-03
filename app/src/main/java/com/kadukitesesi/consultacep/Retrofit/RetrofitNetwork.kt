package com.kadukitesesi.consultacep.Retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitNetwork {

    companion object {
        fun getInstanceUrl(path: String): Retrofit {

            return Retrofit.Builder()
                .baseUrl(path)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        }

    }

}