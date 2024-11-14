package com.akhmedmv.vknewsclient.data.mapper

import android.util.Log
import com.akhmedmv.vknewsclient.data.model.CommentsResponseDto
import com.akhmedmv.vknewsclient.data.model.NewsFeedResponseDto
import com.akhmedmv.vknewsclient.domain.entity.FeedPost
import com.akhmedmv.vknewsclient.domain.entity.PostComment
import com.akhmedmv.vknewsclient.domain.entity.StatisticItem
import com.akhmedmv.vknewsclient.domain.entity.StatisticType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlin.math.absoluteValue

class NewsFeedMapper @Inject constructor() {

    fun mapResponseToPosts(responseDto: NewsFeedResponseDto?): List<FeedPost> {
        if (responseDto == null) {
            Log.e("NewsFeedMapper", "ResponseDto is null")
            return emptyList()
        }

        val result = mutableListOf<FeedPost>()

        try {
            val newsFeedContent = responseDto.newsFeedContent

            val posts = newsFeedContent.posts
            val groups = newsFeedContent.groups

            for (post in posts) {
                val group = groups.find { it.id == post.communityId.absoluteValue }
                    ?: continue

                val feedPost = FeedPost(
                    id = post.id,
                    communityId = post.communityId,
                    communityName = group.name,
                    publicationDate = mapTimestampToDate(post.date),
                    communityImageUrl = group.imageUrl,
                    contentText = post.text,
                    contentImageUrl = post.attachments?.firstOrNull()?.photo?.photoUrls?.lastOrNull()?.url,
                    statistics = listOf(
                        StatisticItem(type = StatisticType.LIKES, post.likes.count),
                        StatisticItem(type = StatisticType.VIEWS, post.views.count),
                        StatisticItem(type = StatisticType.SHARES, post.reposts.count),
                        StatisticItem(type = StatisticType.COMMENTS, post.comments.count)
                    ),
                    isLiked = post.likes.userLikes > 0
                )
                result.add(feedPost)
            }
        } catch (e: Exception) {
            Log.e("NewsFeedMapper", "Error mapping response to posts", e)
            return emptyList()
        }

        return result
    }

    fun mapResponseToComments(response: CommentsResponseDto): List<PostComment> {
        val result = mutableListOf<PostComment>()
        val comments = response.content.comments
        val profiles = response.content.profiles
        for (comment in comments) {
            if (comment.text.isBlank()) continue
            val author = profiles.firstOrNull { it.id == comment.authorId } ?: continue
            val postComment = PostComment(
                id = comment.id,
                authorName = "${author.firstName} ${author.lastName}",
                authorAvatarUrl = author.avatarUrl,
                commentText = comment.text,
                publicationDate = mapTimestampToDate(comment.date)
            )
            result.add(postComment)
        }
        return result
    }

    private fun mapTimestampToDate(timestamp: Long): String {
        val date = Date(timestamp * 1000)
        return SimpleDateFormat("d MMMM yyyy, hh:mm", Locale.getDefault()).format(date)
    }
}