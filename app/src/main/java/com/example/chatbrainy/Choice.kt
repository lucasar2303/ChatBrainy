package com.example.chatbrainy

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Choice {
    @SerializedName("text")
    @Expose
    var text: String? = null

    @SerializedName("index")
    @Expose
    var index = 0

    @SerializedName("finish_reason")
    @Expose
    var finishReason: String? = null

}