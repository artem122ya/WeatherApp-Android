package artem122ya.weatherapp.util

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//class NetworkUtlis private constructor() {
//    companion object {
        private val OK_HTTP_CLIENT = OkHttpClient()

        val RETROFIT : Retrofit = Retrofit.Builder()
                .baseUrl(Constants.API_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .client(OK_HTTP_CLIENT)
                .build()
//    }
//}