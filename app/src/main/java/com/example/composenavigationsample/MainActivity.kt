package com.example.composenavigationsample

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.composenavigationsample.logging.LogBackStackEffect
import com.example.composenavigationsample.logging.LogBackStackEntryEffect
import com.example.composenavigationsample.logging.LogNavGraphEffect
import com.example.composenavigationsample.navigation.birdsGraph
import com.example.composenavigationsample.navigation.dialogGraph
import com.example.composenavigationsample.navigation.nestedSampleGraph
import com.example.composenavigationsample.ui.theme.ComposeNavigationSampleTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("RestrictedApi")
    @OptIn(ExperimentalMaterial3Api::class)
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
                    topBar = {
                        TopAppBar(
                            title = {
                                Text("${backStackEntry?.destination?.route?.removePrefix("$packageName.")}")
                            },
                            navigationIcon = {
                                if (backStackEntry?.destination?.route != BirdList::class.qualifiedName) {
                                    IconButton(onClick = { navController.popBackStack() }) {
                                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                                    }
                                }
                            },
                        )
                    },
                ) { innerPadding ->

                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = BirdList,
                    ) {
                        birdsGraph(
                            onListItemClick = { birdId ->
                                navController.navigate(BirdDetail(id = birdId))
                            },
                            onBirdDetailClick = {
                                navController.navigate(DialogDestinationSample(it))
                            },
                        )

                        nestedSampleGraph()

                        mainGraph()

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
