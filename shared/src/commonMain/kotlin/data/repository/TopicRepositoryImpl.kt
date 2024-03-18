package data.repository

import domain.entity.Question
import domain.entity.Quiz
import domain.entity.Topic
import domain.repository.QuizRepository
import domain.repository.TopicRepository
import kotlinx.coroutines.*

class TopicRepositoryImpl : TopicRepository {
    private val questions = listOf(
        Question("Question 1", rating = 0),
        Question("Question 2", rating = 0),
        Question("Question 3", rating = 0)
    )
    private val list = listOf<Quiz>(
        Quiz("Test Quiz 1", questions = questions),
        Quiz("Test Quiz 2", questions = questions),
    )

    override suspend fun getAllTopics(): List<Topic> {
        delay(2000)

        val topics = listOf(
            Topic(1, "Topic Test 1"),
            Topic(2, "Topic Test 2"),
            Topic(3, "Topic Test 3"),
        )

        return topics
    }

    override suspend fun getTopicQuizzes(id: Int): List<Quiz> {
        delay(1000)
        return list
    }

    override suspend fun deleteTopic(id: Int) {
        TODO("Not yet implemented")
    }

}