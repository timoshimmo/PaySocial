package app.com.android.paybills.util.services

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    companion object {
        fun getClient() : RestApiService {
            val retrofit = Retrofit.Builder()

                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://paysocial-db.herokuapp.com")
                    .build()

            return retrofit.create(RestApiService::class.java)
        }
    }
}