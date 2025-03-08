package com.example.composenavigationsample.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

fun NavGraphBuilder.mainGraph() {
    composable<MainRoute.Main> {
        MainNavHost()
    }
}

@Composable
fun MainNavHost() {
    val navController = rememberNavController()
    val backstack by navController.currentBackStackEntryAsState()
    Scaffold(
        bottomBar = {
            NavigationBar {
                topLevelRoutes.forEach { topLevelRoute ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = topLevelRoute.icon,
                                contentDescription = topLevelRoute.name,
                            )
                        },
                        label = { Text(topLevelRoute.name) },
                        selected = backstack?.destination?.route == topLevelRoute.route::class.qualifiedName,
                        onClick = {
                            navController.navigate(topLevelRoute.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                    )
                }
            }
        },
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = MainRoute.Home,
        ) {
            homeRoute()
            searchRoute()
            settingsRoute()
        }
    }
}

fun NavGraphBuilder.homeRoute() {
    composable<MainRoute.Home> {
        Text("Home")
    }
}

fun NavGraphBuilder.searchRoute() {
    composable<MainRoute.Search> {
        Text("Search")
    }
}

fun NavGraphBuilder.settingsRoute() {
    composable<MainRoute.Settings> {
        Text("Settings")
    }
}

sealed interface MainRoute {
    @Serializable
    @SerialName("Main")
    data object Main : MainRoute

    @Serializable
    data object Home : MainRoute

    @Serializable
    data object Search : MainRoute

    @Serializable
    data object Settings : MainRoute
}

data class TopLevelRoute<T : Any>(val name: String, val route: T, val icon: ImageVector)

private val topLevelRoutes = listOf(
    TopLevelRoute("Home", MainRoute.Home, Icons.Default.Home),
    TopLevelRoute("Search", MainRoute.Search, Icons.Default.Search),
    TopLevelRoute("Settings", MainRoute.Settings, Icons.Default.Settings),
)
