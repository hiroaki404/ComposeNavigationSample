package com.example.composenavigationsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composenavigationsample.ui.theme.ComposeNavigationSampleTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            ComposeNavigationSampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = Screen1,
                    ) {
                        composable<Screen1> {
                            Column {
                                Text("Screen 1")
                                Button(onClick = {
                                    navController.navigate(Screen2)
                                }) {
                                    Text("Go to Screen 2")
                                }
                            }
                        }
                        composable<Screen2> {
                            Column {
                                Text("Screen 2")
                                Button(onClick = {
                                    navController.navigate(Screen1)
                                }) {
                                    Text("Go to Screen 1")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Serializable
object Screen1

@Serializable
object Screen2
