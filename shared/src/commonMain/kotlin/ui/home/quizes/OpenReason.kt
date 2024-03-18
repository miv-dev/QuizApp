package ui.home.quizes

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import domain.entity.Topic

sealed interface OpenReason: Parcelable {
    @Parcelize
    data object AllQuizzes : OpenReason

    @Parcelize
    data class TopicQuizzes(val topic: Topic) : OpenReason
}