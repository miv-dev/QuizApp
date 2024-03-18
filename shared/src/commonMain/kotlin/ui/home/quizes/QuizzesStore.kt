package ui.home.quizes

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import domain.entity.Question
import domain.entity.Quiz
import domain.entity.Topic
import domain.usecase.GetAllQuizzesUseCase
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

interface QuizzesStore : Store<QuizzesStore.Intent, QuizzesStore.State, QuizzesStore.Label> {
    sealed interface Intent {

    }

    sealed class Label {
        
    }

    data class State(
        val quizzesState: QuizzesState
    ) {
        sealed interface QuizzesState {
            data object Initial : QuizzesState
            data object Loading : QuizzesState
            data object Error : QuizzesState
            data object Empty : QuizzesState
            data class Success(val quizzes: List<Quiz>) : QuizzesState
        }
    }
}

class QuizzesStoreFactory(
    private val storeFactory: StoreFactory,
    private val getAllQuizzesUseCase: GetAllQuizzesUseCase,
) : KoinComponent {
    fun create(openReason: OpenReason): QuizzesStore =
        object : QuizzesStore,
            Store<QuizzesStore.Intent, QuizzesStore.State, QuizzesStore.Label> by storeFactory.create(
                name = "QuizzesStore",
                initialState = QuizzesStore.State(
                    quizzesState = QuizzesStore.State.QuizzesState.Initial
                ),
                bootstrapper = BootStrapperImpl(openReason),
                executorFactory = { ExecutorImpl() },
                reducer = ReducerImpl,
            ) {}


    private sealed interface Msg {

        data object QuizResultError : Msg
        data object QuizResultLoading : Msg
        data class QuizResultSuccess(val quizzes: List<Quiz>) : Msg
    }

    private sealed interface Action {
        data object FetchAllQuizzes : Action

        data class GetTopicQuizzes(val topic: Topic) : Action
    }

    private class BootStrapperImpl(val openReason: OpenReason) : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            when (openReason) {
                OpenReason.AllQuizzes -> dispatch(Action.FetchAllQuizzes)

                is OpenReason.TopicQuizzes -> dispatch(Action.GetTopicQuizzes(openReason.topic))
            }
        }

    }

    private inner class ExecutorImpl :
        CoroutineExecutor<QuizzesStore.Intent, Action, QuizzesStore.State, Msg, QuizzesStore.Label>() {
        override fun executeAction(action: Action, getState: () -> QuizzesStore.State) {
            when (action) {
                Action.FetchAllQuizzes -> {
                    dispatch(Msg.QuizResultLoading)
                    scope.launch {
                        runCatching {
                            getAllQuizzesUseCase()
                        }.onFailure {
                            dispatch(Msg.QuizResultError)
                        }.onSuccess {
                            dispatch(Msg.QuizResultSuccess(it))
                            
                        }
                    }
                }

                is Action.GetTopicQuizzes -> {
//                    dispatch(Msg.QuizResultSuccess(action.topic.quizzes))
                }
            }
        }

        override fun executeIntent(intent: QuizzesStore.Intent, getState: () -> QuizzesStore.State) {

        }
    }

    private object ReducerImpl : Reducer<QuizzesStore.State, Msg> {
        override fun QuizzesStore.State.reduce(msg: Msg): QuizzesStore.State = when (msg) {
            Msg.QuizResultError -> copy(quizzesState = QuizzesStore.State.QuizzesState.Error)
            Msg.QuizResultLoading -> copy(quizzesState = QuizzesStore.State.QuizzesState.Loading)
            is Msg.QuizResultSuccess -> copy(quizzesState = QuizzesStore.State.QuizzesState.Success(msg.quizzes))
        }
    }
}



