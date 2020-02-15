package com.techdatum.epanchayat_application.webservices

import com.techdatum.epanchayat_application.webservices.API
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


class WebServiceClass {
    companion object {


        fun callWebServices(): API {
            /* val retrofit: Retrofit = Retrofit.Builder()
                 .baseUrl(API.BASE_URL)
                 .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                 .addConverterFactory(GsonConverterFactory.create(gson))
                 .build()*/
            /*val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()*/

            val client = OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .connectTimeout(5, TimeUnit.MINUTES)
                .build()

            val adapterBuilder = Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(API.BASE_URL)
                .build()
            return adapterBuilder.create(API::class.java)


        }
        fun callWebServicesverficationotp(): API {
            /* val retrofit: Retrofit = Retrofit.Builder()
                 .baseUrl(API.BASE_URL)
                 .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                 .addConverterFactory(GsonConverterFactory.create(gson))
                 .build()*/
            /*val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()*/

            val client = OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .connectTimeout(5, TimeUnit.MINUTES)
                .build()

            val adapterBuilder = Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(API.Base_Url_verification)
                .build()
            return adapterBuilder.create(API::class.java)


        }
        fun callProfileImageUpload(): API {
            var client = OkHttpClient.Builder()
            client.connectTimeout(30, TimeUnit.SECONDS)
            client.readTimeout(30, TimeUnit.SECONDS)
            client.writeTimeout(30, TimeUnit.SECONDS)
            val adapterBuilder = Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .baseUrl(API.BASE_URL)
                .build()
            var api: API = adapterBuilder.create(API::class.java)
            return api
        }

    }
}