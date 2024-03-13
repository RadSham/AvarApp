package my.exam.avarapp.model

data class CategoryPhraseEntity(
    val categoryphrasename: String = "categoryphrasename",
    val categoryphraselist: List<PhraseEntity>
)

data class PhraseEntity(
    val rusphrase: String = "rusphrase",
    val avphrase: String = "avphrase"
)