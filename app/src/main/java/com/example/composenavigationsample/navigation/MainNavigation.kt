package com.example.composenavigationsample.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// ⚠️This method is not recommended. It causes issues with deeplink handling and results in two navControllers, making it more complex.
fun NavGraphBuilder.mainGraph(onGoToBirdListButtonClick: () -> Unit) {
    composable<MainRoute.Main> {
        MainNavHost(onGoToBirdListButtonClick)
    }
}

// ⚠️This method is not recommended. It causes issues with deeplink handling and results in two navControllers, making it more complex.
// But it is useful for bottom navigation sample.
@Composable
fun MainNavHost(onGoToBirdListButtonClick: () -> Unit) {
    val mainNavController = rememberNavController()
    val backstack by mainNavController.currentBackStackEntryAsState()
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
                        selected = backstack?.destination?.hierarchy?.any {
                            it.route == topLevelRoute.route::class.qualifiedName
                        } ?: false,
                        onClick = {
                            mainNavController.navigate(topLevelRoute.route) {
                                popUpTo(mainNavController.graph.findStartDestination().id) {
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
            navController = mainNavController,
            startDestination = MainRoute.Home,
        ) {
            homeRoute(onGoToBirdListButtonClick)
            searchRoute()
            settingsRoute(
                goToAccount = { mainNavController.navigate(MainRoute.Account) },
                goToNotification = { mainNavController.navigate(MainRoute.Notification) },
            )
        }
    }
}

fun NavGraphBuilder.homeRoute(onGoToBirdListButtonClick: () -> Unit) {
    composable<MainRoute.Home> {
        Column {
            Text("Welcome to Bird Dictionary!")
            Button(
                onClick = onGoToBirdListButtonClick,
            ) {
                Text("Go to Bird List!")
            }
        }
    }
}

fun NavGraphBuilder.searchRoute() {
    composable<MainRoute.Search> {
        Text("Search")
    }
}

fun NavGraphBuilder.settingsRoute(
    goToAccount: () -> Unit,
    goToNotification: () -> Unit,
) {
    navigation<MainRoute.Settings>(startDestination = MainRoute.SettingsRows) {
        composable<MainRoute.SettingsRows> {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .clickable(onClick = goToAccount),
                ) {
                    Text("Account")
                }
                HorizontalDivider()
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .clickable(onClick = goToNotification),
                ) {
                    Text("Notification")
                }
                HorizontalDivider()
            }
        }

        composable<MainRoute.Account> {
            Text("Account")
        }

        composable<MainRoute.Notification> {
            Text("Notification")
        }
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

    @Serializable
    data object SettingsRows : MainRoute

    @Serializable
    data object Account : MainRoute

    @Serializable
    data object Notification : MainRoute
}

data class TopLevelRoute<T : Any>(val name: String, val route: T, val icon: ImageVector)

private val topLevelRoutes = listOf(
    TopLevelRoute("Home", MainRoute.Home, Icons.Default.Home),
    TopLevelRoute("Search", MainRoute.Search, Icons.Default.Search),
    TopLevelRoute("Settings", MainRoute.Settings, Icons.Default.Settings),
)
