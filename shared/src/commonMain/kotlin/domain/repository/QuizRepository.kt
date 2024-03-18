package domain.repository

import domain.entity.Quiz

interface QuizRepository {
    suspend fun getAllQuizzes(): List<Quiz>
    
    suspend fun deleteQuiz(id: Int)
}