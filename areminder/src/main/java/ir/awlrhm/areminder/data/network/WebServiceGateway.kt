package ir.awlrhm.areminder.data.network

import ir.awlrhm.areminder.BuildConfig
import ir.awlrhm.areminder.data.local.PreferenceConfiguration
import ir.awlrhm.areminder.utils.SSID
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

internal class WebServiceGateway(
    private val pref: PreferenceConfiguration
) {
    val retrofit: Retrofit
        get() {
            val client = OkHttpClient.Builder().apply {
                if (BuildConfig.DEBUG) {
                    connectTimeout(300, TimeUnit.SECONDS)
                    readTimeout(300, TimeUnit.SECONDS)
                    writeTimeout(300, TimeUnit.SECONDS)
                } else {
                    connectTimeout(180, TimeUnit.SECONDS)
                    readTimeout(180, TimeUnit.SECONDS)
                    writeTimeout(180, TimeUnit.SECONDS)
                }
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(logging)

                addInterceptor(Interceptor { chain ->
                    val newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer ${pref.accessToken}")
                        .addHeader("IMEI", pref.imei)
                        .addHeader("OS_VERSION", pref.osVersion)
                        .addHeader("DEVICE_MODEL", pref.deviceModel)
                        .addHeader("APP_VERSION_CODE", pref.appVersion)
                        .addHeader("SSID", "$SSID")
                        .addHeader("OS_TYPE", "android")
                        .addHeader("MAC_ADDRESS", "0")
                        .addHeader("IP_ADDRESS", "0")
                        .addHeader("COMPUTER_NAME", "0")
                        .build()
                    chain.proceed(newRequest)
                })

            }.build()

            return Retrofit.Builder().apply {
                baseUrl(pref.hostName)
                addConverterFactory(GsonConverterFactory.create())
                client(client)
            }.build()
        }
}