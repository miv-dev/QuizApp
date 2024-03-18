package ui.quiz

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import domain.entity.Question
import org.koin.core.component.KoinComponent

interface QuizStore : Store<QuizStore.Intent, QuizStore.State, QuizStore.Label> {
    sealed interface Intent {
        data object NextQuestion : Intent
        data class ChangeRating(val rating: Int) : Intent
        data object PrevQuestion : Intent

    }

    sealed class Label {

    }

    data class State(
        val currentQuiz: Int,
        val questions: List<Question>
    )
}

class QuizStoreFactory(
    private val storeFactory: StoreFactory
) : KoinComponent {
    fun create(): QuizStore =
        object : QuizStore, Store<QuizStore.Intent, QuizStore.State, QuizStore.Label> by storeFactory.create(
            name = "QuizStore",
            initialState = QuizStore.State(
                currentQuiz = 0,
                questions = listOf(Question("Question 1", rating = 0), Question("Question 2", rating = 0))
            ),
            bootstrapper = BootStrapperImpl(),
            executorFactory = { ExecutorImpl() },
            reducer = ReducerImpl,
        ) {}


    private sealed class Msg {
        data class NewQuestion(val id: Int) : Msg()
        data class ChangeQuestionRating(val questions: List<Question>) : Msg()


    }

    private sealed class Action {}

    private class BootStrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {

        }

    }

    private inner class ExecutorImpl :
        CoroutineExecutor<QuizStore.Intent, Action, QuizStore.State, Msg, QuizStore.Label>() {
        override fun executeIntent(intent: QuizStore.Intent, getState: () -> QuizStore.State) {
            when (intent) {
                is QuizStore.Intent.ChangeRating -> {
                    val currentQuiz = getState().currentQuiz
                    val questions = getState().questions
                    val newQuestions =
                        questions.mapIndexed { index, quiz -> if (index == currentQuiz) quiz.copy(rating = intent.rating) else quiz }

                    dispatch(Msg.ChangeQuestionRating(newQuestions))


                }

                QuizStore.Intent.NextQuestion -> {
                    val currentQuiz = getState().currentQuiz
                    val questions = getState().questions
                    if (questions.size > currentQuiz + 1 ) {
                        dispatch(Msg.NewQuestion(getState().currentQuiz + 1))
                    }
                }

                QuizStore.Intent.PrevQuestion -> {
                    val currentQuiz = getState().currentQuiz
                    if (0 < currentQuiz){
                        dispatch(Msg.NewQuestion(getState().currentQuiz - 1))
                    }
                }
            }

        }
    }

    private object ReducerImpl : Reducer<QuizStore.State, Msg> {
        override fun QuizStore.State.reduce(msg: Msg): QuizStore.State = when (msg) {
            is Msg.ChangeQuestionRating -> {
                copy(questions = msg.questions)
            }

            is Msg.NewQuestion -> copy(currentQuiz = msg.id)
        }
    }
}



