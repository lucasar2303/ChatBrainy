package com.example.chatbrainy.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
class ApiResponse {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("object")
    @Expose
    var objectText: String? = null

    @SerializedName("created")
    @Expose
    var created = 0

    @SerializedName("model")
    @Expose
    var model: String? = null

    @SerializedName("choices")
    @Expose
    var choices: List<Choice>? = null

    @SerializedName("usage")
    @Expose
    var usage: Usage? = null

}