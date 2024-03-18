package ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import org.koin.core.Koin
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf
import ui.home.DefaultHomeComponent
import ui.home.HomeComponent
import ui.quiz.DefaultQuizComponent
import ui.root.RootComponent.Child

class DefaultRootComponent constructor(
    componentContext: ComponentContext
) : KoinComponent, RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, Child>>
        get() = childStack(
            key = "RootStack",
            source = navigation,
            initialConfiguration = Config.Home,
            childFactory = ::child
        )


    private fun child(config: Config, context: ComponentContext): Child =
        when (config) {
            Config.Home -> Child.Home(component = homeComponent(context))
        }

    private fun quizComponent(componentContext: ComponentContext): DefaultQuizComponent {
        return getKoin().get<DefaultQuizComponent> {
            parametersOf(
                componentContext
            )
        }
    }
    
    private fun homeComponent(componentContext: ComponentContext): HomeComponent {
        return getKoin().get<DefaultHomeComponent> {
            parametersOf(
                componentContext
            )
        }
    }

    private sealed interface Config : Parcelable {
        @Parcelize
        data object Home : Config

    }
}