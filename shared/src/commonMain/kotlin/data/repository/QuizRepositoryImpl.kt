package data.repository

import domain.entity.Question
import domain.entity.Quiz
import domain.repository.QuizRepository
import kotlinx.coroutines.*

class QuizRepositoryImpl : QuizRepository {

    val scope = CoroutineScope(Dispatchers.IO)
    override suspend fun getAllQuizzes(): List<Quiz> {
        delay(2000)
        val questions = listOf(
            Question("Question 1", rating = 0),
            Question("Question 2", rating = 0),
            Question("Question 3", rating = 0)
        )
        val list = listOf<Quiz>(
            Quiz("Test Quiz 1", questions = questions),
            Quiz("Test Quiz 2", questions = questions),
        )
        return list
    }

    override suspend fun deleteQuiz(id: Int) {
        TODO("Not yet implemented")
    }

}