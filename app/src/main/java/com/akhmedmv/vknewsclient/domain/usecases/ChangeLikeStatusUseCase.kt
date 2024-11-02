package com.akhmedmv.vknewsclient.domain.usecases

import com.akhmedmv.vknewsclient.domain.entity.FeedPost
import com.akhmedmv.vknewsclient.domain.repository.NewsFeedRepository

class ChangeLikeStatusUseCase(
    private val repository: NewsFeedRepository
) {
    suspend operator fun invoke(feedPost: FeedPost) {
        repository.changeLikeStatus(feedPost)
    }
}