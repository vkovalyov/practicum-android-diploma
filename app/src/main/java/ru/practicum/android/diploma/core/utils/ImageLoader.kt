package ru.practicum.android.diploma.core.utils

import android.content.Context
import coil.ImageLoader
import okhttp3.OkHttpClient

object ImageLoader {
    private var _imageLoader: ImageLoader? = null

    fun get(context: Context): ImageLoader {
        return _imageLoader ?: ImageLoader.Builder(context.applicationContext)
            .okHttpClient {
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        chain.request().newBuilder()
                            .addHeader(
                                "User-Agent",
                                "Mozilla/5.0 (Linux; Android 13) " +
                                    "AppleWebKit/537.36 (KHTML, like Gecko) " +
                                    "Chrome/120.0.0.0 Mobile Safari/537.36"
                            )
                            .addHeader("Referer", "https://www.google.com/")
                            .build()
                            .let(chain::proceed)
                    }
                    .build()
            }
            .build()
            .also { _imageLoader = it }
    }
}
