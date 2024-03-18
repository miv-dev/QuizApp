package ru.miv.dev

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import ru.miv.dev.theme.QuizAppTheme
import ui.root.RootComponent


@Composable
fun RootContent(component: RootComponent) {
    QuizAppTheme {
        Children(stack = component.stack) { child ->
            when (val child = child.instance) {
                is RootComponent.Child.Home -> HomeContent(child.component)
            }
        }

    }
}