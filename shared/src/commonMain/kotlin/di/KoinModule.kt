package di


import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import data.repository.QuizRepositoryImpl
import data.repository.TopicRepositoryImpl
import domain.repository.QuizRepository
import domain.repository.TopicRepository
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import domain.usecase.GetAllQuizzesUseCase
import ui.home.DefaultHomeComponent
import ui.home.HomeComponent
import ui.home.quizes.DefaultQuizzesComponent
import ui.home.quizes.QuizzesStoreFactory
import ui.quiz.DefaultQuizComponent
import ui.quiz.QuizStoreFactory
import ui.root.DefaultRootComponent
import ui.root.RootComponent

fun initKoin(componentContext: ComponentContext) = startKoin {

    modules(
        module {
            single<RootComponent> {
                DefaultRootComponent(componentContext)
            }

            single<StoreFactory> {
                DefaultStoreFactory()
            }
            single<QuizStoreFactory> {
                QuizStoreFactory(storeFactory = get())
            }
            single<QuizzesStoreFactory> {
                QuizzesStoreFactory(storeFactory = get(), getAllQuizzesUseCase = get())
            }
            factory<DefaultQuizComponent> { p ->
                DefaultQuizComponent(
                    factory = get(),
                    componentContext = p[0]
                )
            }
            factory<DefaultQuizzesComponent> { p ->
                DefaultQuizzesComponent(
                    factory = get(),
                    componentContext = p[0],
                    openReason = p[1]
                )
            }
            factory<DefaultHomeComponent> { p ->
                DefaultHomeComponent(
                    componentContext = p[0]
                )
            }
        },
        useCaseModule,
        repositoryModule,
    )
}

val repositoryModule = module {
    single<TopicRepository> { TopicRepositoryImpl() }
    single<QuizRepository> { QuizRepositoryImpl() }
}


val useCaseModule = module {
    single { GetAllQuizzesUseCase(repository = get()) }
}