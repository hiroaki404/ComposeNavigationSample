package com.example.composenavigationsample

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.composenavigationsample.logging.LogBackStackEffect
import com.example.composenavigationsample.logging.LogBackStackEntryEffect
import com.example.composenavigationsample.logging.LogNavGraphEffect
import com.example.composenavigationsample.navigation.MainRoute
import com.example.composenavigationsample.navigation.birdsGraph
import com.example.composenavigationsample.navigation.dialogGraph
import com.example.composenavigationsample.navigation.mainGraph
import com.example.composenavigationsample.navigation.nestedSampleGraph
import com.example.composenavigationsample.ui.theme.ComposeNavigationSampleTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val backStackEntry by navController.currentBackStackEntryAsState()
            val backstack by navController.currentBackStack.collectAsState()

            LogBackStackEntryEffect(backStackEntry)
            LogBackStackEffect(backstack)

            ComposeNavigationSampleTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    contentWindowInsets = WindowInsets(0, 0, 0, 0),
                    topBar = {
                        val isTopBarVisible by remember {
                            derivedStateOf {
                                backStackEntry?.destination?.route?.contains("Main") == false
                            }
                        }
                        OutsideTopBar(
                            isTopBarVisible = isTopBarVisible,
                            currentBackstackEntryRoute = backStackEntry?.destination?.route,
                            popBackStack = { navController.popBackStack() },
                            navigateToMain = { navController.navigate(MainRoute.Main) },
                        )
                    },
                ) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = BirdList,
                    ) {
                        birdsGraph(navController)

                        nestedSampleGraph()

                        // ⚠️ Be cautious when using this method. It can result in complexity, as it requires two navControllers and can cause issues with deeplink handling.
                        // However, Depending on the situation, this might be a good approach.
                        mainGraph(onGoToBirdListButtonClick = { navController.navigate(BirdList) })

                        dialogGraph(navController)
                    }

                    LogNavGraphEffect(navController.graph)
                }
            }
        }
    }
}

@Serializable
object BirdList

@Serializable
data class BirdDetail(val id: Int)

@Serializable
data class DialogDestinationSample(val id: Int)
