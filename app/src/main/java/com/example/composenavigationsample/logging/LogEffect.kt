package com.example.composenavigationsample.logging

import android.content.Context
import android.util.Log
import androidx.collection.forEach
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph

@Composable
fun Context.LogBackStackEntryEffect(backStackEntry: NavBackStackEntry?) {
    LaunchedEffect(backStackEntry) {
        backStackEntry?.destination?.hierarchy?.map {
            it.route?.replace(
                "$packageName.",
                "",
            )?.split("/")?.firstOrNull()?.substringBefore(
                "?",
            )
        }?.joinToString(",")
            ?.let { Log.d("backstackEntry", it) }
    }
}

@Composable
fun Context.LogBackStackEffect(backstack: List<NavBackStackEntry>) {
    LaunchedEffect(backstack) {
        backstack.map {
            it.destination.route?.replace(
                "$packageName.",
                "",
            )?.split("/")?.firstOrNull()?.substringBefore("?")
        }.joinToString(",").let {
            Log.d("backstack", it)
        }
    }
}

@Composable
fun Context.LogNavGraphEffect(navGraph: NavGraph) {
    LaunchedEffect(Unit) {
        navGraph.joinToString("\n") { navDestination ->
            navDestination.childLogString("$packageName.").joinToString("\n")
        }.let {
            Log.d("navGraph", it)
        }
    }
}

private fun String.removeString(removedString: String): String = replace(removedString, "")

private fun NavDestination.childLogString(removedString: String): List<String?> {
    val nodesRoute: MutableList<String?> = mutableListOf()
    nodesRoute.add(("$route".removeString(removedString)))
    (this as? NavGraph)?.nodes?.forEach { _, destination ->
        nodesRoute.addAll(destination.childLogString(removedString).map { "\t$it" })
    }
    return nodesRoute
}
