package com.example.chatbrainy.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Usage {
    @SerializedName("prompt_tokens")
    @Expose
    var promptTokens = 0

    @SerializedName("completion_tokens")
    @Expose
    var completionTokens = 0

    @SerializedName("total_tokens")
    @Expose
    var totalTokens = 0

}