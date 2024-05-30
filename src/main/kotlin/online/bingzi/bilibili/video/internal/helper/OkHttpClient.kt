package online.bingzi.bilibili.video.internal.helper

import okhttp3.OkHttpClient
import online.bingzi.bilibili.video.internal.interceptor.ReceivedCookiesInterceptor
import online.bingzi.bilibili.video.internal.interceptor.UserAgentInterceptor

internal val client = OkHttpClient.Builder().addInterceptor(ReceivedCookiesInterceptor())
    .addInterceptor(UserAgentInterceptor("BilibiliBot")).build()
