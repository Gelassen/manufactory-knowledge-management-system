package io.github.gelassen.manufactory_knowledge_management.di

import dagger.Module
import dagger.Provides
import io.github.gelassen.manufactory_knowledge_management.AppApplication
import io.github.gelassen.manufactory_knowledge_management.R
import io.github.gelassen.manufactory_knowledge_management.network.IApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule(val application: AppApplication) {

    companion object {
        const val DISPATCHER_IO = "Dispatchers.IO"
        const val DISPATCHER_MAIN = "Dispatchers.Main"
    }

/*    @Singleton
    @Provides
    fun providesApi(httpClient: OkHttpClient) : IApi {
        val url = application.getString(R.string.main_endpoint)
        val retrofit = Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create(customGson))
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .baseUrl(url)
            .build()

        return retrofit.create(IApi::class.java)
    }*/

    @Singleton
    @Provides
    fun providesOkHttpClient() : OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpClient = OkHttpClient
            .Builder()
            .addInterceptor(logging)
            .build()

        return httpClient
    }

/*    @Singleton
    @Provides
    @Named(DISPATCHER_IO)
    fun providesNetworkDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }*/
}