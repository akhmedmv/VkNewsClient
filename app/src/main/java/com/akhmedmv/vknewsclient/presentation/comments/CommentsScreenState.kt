package com.akhmedmv.vknewsclient.presentation.comments

import com.akhmedmv.vknewsclient.domain.entity.FeedPost
import com.akhmedmv.vknewsclient.domain.entity.PostComment

sealed class CommentsScreenState {

    object Initial : CommentsScreenState()

    data class Comments(
        val feedPost: FeedPost,
        val comments: List<PostComment>
    ) : CommentsScreenState()
}
