package ui.quiz

import com.arkivanov.decompose.value.Value

interface QuizComponent {
    val model: Value<QuizStore.State>

    fun changeRating(rating: Int)

    fun onClickNext()
    
    fun onClickPrev()
}