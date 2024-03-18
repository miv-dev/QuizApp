package domain.usecase

import domain.repository.QuizRepository

class GetAllQuizzesUseCase(
    private val repository: QuizRepository
) {
    suspend operator fun invoke() = repository.getAllQuizzes()
}