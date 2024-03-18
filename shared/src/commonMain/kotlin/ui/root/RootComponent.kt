package ui.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import ui.home.HomeComponent
import ui.quiz.QuizComponent

interface RootComponent {
    val stack: Value<ChildStack<*, Child>>

    sealed interface Child {
        
        data class Home(val component: HomeComponent): Child
        
    }
}