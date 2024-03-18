package ui.home.quizes

import com.arkivanov.decompose.value.Value

interface QuizzesComponent {
    
    val model: Value<QuizzesStore.State>
    
}