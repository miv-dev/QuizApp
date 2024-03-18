package domain.entity

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
data class Topic(
    val id: Int,
    val name: String,
): Parcelable