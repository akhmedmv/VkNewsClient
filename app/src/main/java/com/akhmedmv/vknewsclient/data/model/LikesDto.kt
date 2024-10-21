package com.akhmedmv.vknewsclient.data.model

import com.google.gson.annotations.SerializedName

data class LikesDto(
    @SerializedName("like") val count: Int,
)
