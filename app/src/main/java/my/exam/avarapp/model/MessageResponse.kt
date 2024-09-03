package my.exam.avarapp.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class MessageResponse(
    @SerializedName("message") var message: String,
    @SerializedName("sent_by") var sent_by: String,
    @SerializedName("user_name") var user_name: String,
    @SerializedName("sent_on") var sent_on: Long,
    @SerializedName("reply_to_id") var reply_to_id: String,
    @SerializedName("reply_to_text") var reply_to_text: String,
    @SerializedName("reply_to_username") var reply_to_username: String
)