package com.alientodevida.alientoapp.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent


@Module
@InstallIn(ApplicationComponent::class)
abstract class AppModule {

}

/*@Module
@InstallIn(ApplicationComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindRepository(impl: RepositoryImpl): Repository

    @Binds
    abstract fun bindRemoteDataSource(impl: RemoteDataSourceImpl): RemoteDataSource
}

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {

    @Provides
    fun providesRetrofitService(): RetrofitService {
        val httpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()

        addLoggingInterceptor(httpBuilder)

        val httpClient = httpBuilder
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(httpClient)
            .build()

        return retrofit.create(RetrofitService::class.java)
    }

    private fun addLoggingInterceptor(httpBuilder: OkHttpClient.Builder) {
        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        httpBuilder.addInterceptor(logging)
    }


}*/