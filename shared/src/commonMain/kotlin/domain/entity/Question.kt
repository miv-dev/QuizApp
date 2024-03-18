package domain.entity

data class Question(
    val question: String,
    val rating: Int
)

data class Quiz(
    val title: String, 
    val questions: List<Question>
)