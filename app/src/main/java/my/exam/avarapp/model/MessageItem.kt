package my.exam.avarapp.model

data class MessageItem(
    var message: String,
    var sentBy: String,
    var userName: String,
    var sentOn: Long,
    var replyToId: String,
    var replyToText: String,
    var replyToUsername: String,
    var isCurrentUser: Boolean
)