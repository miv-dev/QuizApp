package ui.home

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import ui.home.quizes.QuizzesComponent


interface HomeComponent {

    val stack: Value<ChildStack<*, Child>>

    fun navigateToQuizes()
    fun navigateToStatistics()
    fun navigateToTopics()
    
    sealed interface Child {

        data class Quizes(val component: QuizzesComponent) : Child
        data object Statistics : Child
        data object Topics : Child

    }
}
