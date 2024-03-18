package ru.miv.dev

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.defaultComponentContext
import di.initKoin
import org.koin.java.KoinJavaComponent.getKoin
import ui.root.RootComponent

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initKoin(defaultComponentContext())

        val root: RootComponent = getKoin().get()


        setContent {
            RootContent(root)
        }
    }
}
