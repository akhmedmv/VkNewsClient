package com.akhmedmv.vknewsclient.data.network

import com.akhmedmv.vknewsclient.data.model.NewsFeedResponseDto
import com.vk.api.sdk.auth.VKAccessToken
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("newsfeed.getRecommended?v=5.199")
    suspend fun loadRecommendations(
        @Query("access_token") token: String
    ): NewsFeedResponseDto
}