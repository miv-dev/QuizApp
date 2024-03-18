package domain.repository

import domain.entity.Quiz
import domain.entity.Topic

interface TopicRepository {
    suspend fun getAllTopics(): List<Topic>
    
    suspend fun getTopicQuizzes(id: Int): List<Quiz>
    
    suspend fun deleteTopic(id: Int)
}