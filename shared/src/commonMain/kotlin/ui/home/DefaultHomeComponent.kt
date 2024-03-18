package ui.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf
import ui.home.HomeComponent.Child
import ui.home.quizes.DefaultQuizzesComponent
import ui.home.quizes.OpenReason
import ui.home.quizes.QuizzesComponent

class DefaultHomeComponent(
    componentContext: ComponentContext,
) : KoinComponent, HomeComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, Child>>
        get() = childStack(
            key = "HomeStack",
            source = navigation,
            initialConfiguration = Config.Quizes(OpenReason.AllQuizzes),
            childFactory = ::child
        )

    override fun navigateToQuizes() {
        navigation.replaceAll(Config.Quizes(OpenReason.AllQuizzes))
    }

    override fun navigateToStatistics() {
        navigation.replaceAll(Config.Statistics)
    }

    override fun navigateToTopics() {
        navigation.replaceAll(Config.Topics)
    }


    private fun child(config: Config, componentContext: ComponentContext): Child =
        when (config) {
            is Config.Quizes -> Child.Quizes(quizzesComponent(componentContext, config.openReason))
            Config.Statistics -> Child.Statistics
            Config.Topics -> Child.Topics
        }

    private fun quizzesComponent(componentContext: ComponentContext, openReason: OpenReason): QuizzesComponent {
        return getKoin().get<DefaultQuizzesComponent> {
            parametersOf(
                componentContext,
                openReason
            )
        }
    }
    
    
    private sealed interface Config : Parcelable {
        @Parcelize
        data class Quizes(val openReason: OpenReason) : Config

        @Parcelize
        data object Statistics : Config

        @Parcelize
        data object Topics : Config


    }
    


}