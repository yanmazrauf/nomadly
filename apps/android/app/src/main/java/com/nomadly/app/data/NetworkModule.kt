package com.nomadly.app.data

/**
 * Retrofit + OkHttp setup.
 *
 * Currently stubbed out. Uncomment when the backend is available and update
 * [com.nomadly.app.data.di.RepositoryProvider] to use RemoteXRepository implementations.
 *
 * Required Gradle deps (already declared in libs.versions.toml):
 *   - com.squareup.retrofit2:retrofit
 *   - com.squareup.retrofit2:converter-gson
 *   - com.squareup.okhttp3:logging-interceptor
 */
object NetworkModule {

    const val BASE_URL = "https://api.nomadly.app/v1/"

    // val loggingInterceptor = HttpLoggingInterceptor().apply {
    //     level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
    //             else HttpLoggingInterceptor.Level.NONE
    // }

    // val okHttpClient: OkHttpClient = OkHttpClient.Builder()
    //     .connectTimeout(30, TimeUnit.SECONDS)
    //     .readTimeout(30, TimeUnit.SECONDS)
    //     .addInterceptor(loggingInterceptor)
    //     // TODO: add auth interceptor here (e.g. JWT bearer token)
    //     .build()

    // val retrofit: Retrofit = Retrofit.Builder()
    //     .baseUrl(BASE_URL)
    //     .client(okHttpClient)
    //     .addConverterFactory(GsonConverterFactory.create())
    //     .build()

    // val apiService: NomadlyApiService = retrofit.create(NomadlyApiService::class.java)
}
