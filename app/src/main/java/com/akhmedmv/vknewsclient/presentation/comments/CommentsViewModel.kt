package com.akhmedmv.vknewsclient.presentation.comments

import android.app.Application
import androidx.lifecycle.ViewModel
import com.akhmedmv.vknewsclient.data.repository.NewsFeedRepository
import com.akhmedmv.vknewsclient.domain.FeedPost
import kotlinx.coroutines.flow.map

class CommentsViewModel(
    feedPost: FeedPost,
    application: Application
) : ViewModel() {

    private val repository = NewsFeedRepository(application)

    val screenState = repository.getComments(feedPost)
        .map {
            CommentsScreenState.Comments(
                feedPost = feedPost,
                comments = it
            )
        }
}
