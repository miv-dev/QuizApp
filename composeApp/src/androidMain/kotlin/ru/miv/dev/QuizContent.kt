package ru.miv.dev

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.arkivanov.decompose.value.observe
import ui.quiz.QuizComponent
import kotlin.math.roundToInt


@Composable
fun QuizContent(component: QuizComponent) {
    val model by component.model.subscribeAsState()


    Scaffold { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val questions = model.questions
            val current = model.currentQuiz
            val theme = MaterialTheme.colorScheme

            QuizProgress(indicatorProgress = (current + 1) / questions.size.toFloat())

            Button(onClick = component::onClickPrev) {
                Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Prev Question")
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                for (i in (questions.size - 1) downTo 0) {
                    this@Column.AnimatedVisibility(
                        visible = i >= current,
                        enter = slideInHorizontally() + fadeIn(),
                        exit = slideOutHorizontally() + fadeOut(),

                        ) {
                        val offsetX: Dp by animateDpAsState(if (i <= current) 0.dp else 10.dp)
                        val offsetY: Dp by animateDpAsState(if (i <= current) 0.dp else (-10).dp)



                        QuizCard(
                            quantity = questions.size,
                            current = i + 1,
                            title = questions[i].question, modifier = Modifier

                                .offset(
                                    x = offsetX,
                                    y = offsetY
                                )
                                .clip(shape = RoundedCornerShape(20.dp))
                                .background(
                                    if (i <= current) theme.secondaryContainer else theme.primary,
                                )

                        )


                    }
                }
            }
            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 5.dp)
                ) {
                    for (i in 0..10) {
                        Text(text = "$i", color = theme.primary)
                    }
                }
                val sliderValue: Float by animateFloatAsState(questions[current].rating.toFloat())

                Slider(
                    value = sliderValue,
                    onValueChange = {
                        component.changeRating(it.roundToInt())
                    },
                    steps = 9,
                    valueRange = 0f..10f
                )

            }
            Button(
                onClick = { component.onClickNext() },
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(text = "Следующий вопрос")
                Icon(Icons.AutoMirrored.Rounded.ArrowForward, contentDescription = "Next Question")
            }

        }
    }
}


@Composable
fun QuizProgress(indicatorProgress: Float) {
    var progress by remember { mutableFloatStateOf(0f) }
    val progressAnimDuration = 500
    val progressAnimation by animateFloatAsState(
        targetValue = indicatorProgress,
        animationSpec = tween(durationMillis = progressAnimDuration, easing = FastOutSlowInEasing)
    )
    LinearProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp)), // Rounded edges
        progress = progressAnimation
    )
    LaunchedEffect(indicatorProgress) {
        progress = indicatorProgress
    }
}

@Composable
fun QuizCard(modifier: Modifier = Modifier, title: String, quantity: Int, current: Int) {
    val theme = MaterialTheme.colorScheme
    Box(
        modifier = modifier
            .padding(20.dp)
            .fillMaxWidth()
            .heightIn(min = 300.dp)
    ) {
        Text(
            text = "вопрос $current/$quantity",
            modifier = Modifier.align(Alignment.TopCenter),
            textAlign = TextAlign.Center,
            color = theme.onSecondaryContainer
        )
        Text(
            text = title,
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            color = theme.onSecondaryContainer

        )
    }
}