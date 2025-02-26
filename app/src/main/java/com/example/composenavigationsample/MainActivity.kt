package com.example.composenavigationsample

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.composenavigationsample.ui.BirdDetailScreen
import com.example.composenavigationsample.ui.BirdListScreen
import com.example.composenavigationsample.ui.theme.ComposeNavigationSampleTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val backStackEntry by navController.currentBackStackEntryAsState()
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
                        composable<BirdList> {
                            BirdListScreen { birdId ->
                                navController.navigate(BirdDetail(id = birdId))
                            }
                        }
                        composable<BirdDetail> { backStackEntry ->
                            val birdId = backStackEntry.toRoute<BirdDetail>().id
                            BirdDetailScreen(birdId = birdId)
                        }
                    }
                }
            }
        }
    }
}

@Serializable
object BirdList

@Serializable
data class BirdDetail(val id: Int)

// sealed interface Destination {
//    @Serializable
//    data object BirdList : Destination
//
//    @Serializable
//    data class BirdDetail(val id: Int) : Destination
// }
//
// class BirdListViewModel(
//    savedStateHandle: SavedStateHandle,
// ) : ViewModel() {
//    init {
//        savedStateHandle.toRoute<BirdList>()
//    }
// }
//
