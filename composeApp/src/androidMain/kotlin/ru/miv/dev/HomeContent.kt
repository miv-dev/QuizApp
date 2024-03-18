package ru.miv.dev

import androidx.compose.animation.*
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material.icons.rounded.Quiz
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import ui.home.HomeComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(component: HomeComponent) {
    val stack by component.stack.subscribeAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    val title = when(stack.active.instance){
                        is HomeComponent.Child.Quizes -> "Опросы"
                        HomeComponent.Child.Statistics -> "Статистика"
                        HomeComponent.Child.Topics -> "Темы"
                    }
                    Text(title)
                }
            )
        },
        floatingActionButton = {
            val active = stack.active.instance
            AnimatedVisibility(active == HomeComponent.Child.Topics || active is HomeComponent.Child.Quizes,
                enter = slideInHorizontally(initialOffsetX = { it * 2 }), exit = slideOutHorizontally(targetOffsetX = { it * 2})
            ) {
                FloatingActionButton({}) {
                    Icon(Icons.Rounded.Add, contentDescription = "Add")
                }
            }
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = stack.active.instance is HomeComponent.Child.Quizes,
                    onClick = component::navigateToQuizes,
                    icon = {
                        Icon(Icons.Rounded.Quiz, contentDescription = "Quizes")
                    },

                )
                NavigationBarItem(
                    selected = stack.active.instance == HomeComponent.Child.Topics,
                    onClick = component::navigateToTopics,
                    icon = {
                        Icon(Icons.Rounded.Folder, contentDescription = "Topics")
                    },
                    
                )
                NavigationBarItem(
                    selected = stack.active.instance == HomeComponent.Child.Statistics,
                    onClick = component::navigateToStatistics,
                    icon = {
                        Icon(Icons.Rounded.BarChart, contentDescription = "Statistics")
                    },
                    

                )
            }
        }
    ) {
        Children(stack = stack, modifier = Modifier.padding(it),
            animation = stackAnimation(fade())
            ) { child ->
            when (val instance = child.instance) {
                is HomeComponent.Child.Quizes -> QuizzesContent(instance.component)
                HomeComponent.Child.Topics -> TopicsContent()
                HomeComponent.Child.Statistics -> Text("Statistics")
            }
        }
    }
}
