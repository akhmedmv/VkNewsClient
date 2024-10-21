package com.akhmedmv.vknewsclient.presentation.comments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akhmedmv.vknewsclient.domain.FeedPost
import com.akhmedmv.vknewsclient.domain.PostComment
import kotlin.apply

class CommentsViewModel(
    feedPost: FeedPost
) : ViewModel() {

    private val _screenState = MutableLiveData<CommentsScreenState>(CommentsScreenState.Initial)
    val screenState: LiveData<CommentsScreenState> = _screenState

    init {
        loadComments(feedPost)
    }

    private fun loadComments(feedPost: FeedPost) {
        val comments = mutableListOf<PostComment>().apply {
            repeat(10) {
                add(PostComment(id = it))
            }
        }
        _screenState.value = CommentsScreenState.Comments(
            feedPost = feedPost,
            comments = comments
        )
    }
}
