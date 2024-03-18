package ru.miv.dev

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import ui.home.quizes.QuizzesComponent
import ui.home.quizes.QuizzesStore

@Composable
fun QuizzesContent(component: QuizzesComponent) {
    val model by component.model.subscribeAsState()
    Column (Modifier.padding(horizontal = 12.dp)){

        when (val state = model.quizzesState) {
            QuizzesStore.State.QuizzesState.Empty -> Text("Empty")
            QuizzesStore.State.QuizzesState.Error -> Text("Error")
            QuizzesStore.State.QuizzesState.Initial -> Text("Initial")
            QuizzesStore.State.QuizzesState.Loading -> Text("Loading")
            is QuizzesStore.State.QuizzesState.Success -> {
                LazyColumn(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(state.quizzes) { quiz ->
                        QuizCard(quiz.title) {

                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizCard(title: String, onClick: () -> Unit) {
    val theme = MaterialTheme.colorScheme

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(theme.secondaryContainer, theme.onSecondaryContainer)
    ) {
        Row(Modifier.padding(20.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(title)
        }
    }
}