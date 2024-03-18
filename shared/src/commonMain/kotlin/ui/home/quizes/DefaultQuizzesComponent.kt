package ui.home.quizes

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import ui.ext.componentScope

@OptIn(ExperimentalCoroutinesApi::class)
class DefaultQuizzesComponent(
    openReason: OpenReason,
    private val factory: QuizzesStoreFactory,
    componentContext: ComponentContext
) : KoinComponent, QuizzesComponent, ComponentContext by componentContext {
    private val store = instanceKeeper.getStore { factory.create(openReason = openReason) }
    private val scope = componentScope()

    private val _model: MutableValue<QuizzesStore.State> =
        MutableValue(QuizzesStore.State(QuizzesStore.State.QuizzesState.Loading))

    init {
        scope.launch {
            store.stateFlow.collect { state ->
                _model.update { state }
            }
        }
    }

    override val model: Value<QuizzesStore.State> = _model

    
    
}