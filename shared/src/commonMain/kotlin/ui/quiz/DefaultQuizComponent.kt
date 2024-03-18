package ui.quiz

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import ui.ext.componentScope

@OptIn(ExperimentalCoroutinesApi::class)
class DefaultQuizComponent(
    private val factory: QuizStoreFactory,
    componentContext: ComponentContext
) : QuizComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { factory.create() }
    private val scope = componentScope()

    private val _model: MutableValue<QuizStore.State> =
        MutableValue(QuizStore.State(currentQuiz = 0, questions = emptyList()))

    init {
        scope.launch {
            store.stateFlow.collect { state ->
                _model.update { state }
            }
        }
    }

    override val model: Value<QuizStore.State> = _model

    override fun changeRating(rating: Int) {
        store.accept(QuizStore.Intent.ChangeRating(rating))
    }

    override fun onClickNext() {
        store.accept(QuizStore.Intent.NextQuestion)
    }

    override fun onClickPrev() {
        store.accept(QuizStore.Intent.PrevQuestion)
    }


}