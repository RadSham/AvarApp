package my.exam.avarapp.model

import com.google.gson.annotations.SerializedName

data class MessageResponse(
    @SerializedName("id") var id: Long,
    @SerializedName("message") var message: String,
    @SerializedName("sent_by") var sentBy: String,
    @SerializedName("user_name") var userName: String,
    @SerializedName("sent_on") var sentOn: Long,
    @SerializedName("reply_to_id") var replyToId: String,
    @SerializedName("reply_to_text") var replyToText: String,
    @SerializedName("reply_to_username") var replyToUsername: String
)