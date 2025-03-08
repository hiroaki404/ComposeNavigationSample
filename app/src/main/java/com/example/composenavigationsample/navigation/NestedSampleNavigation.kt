package com.example.composenavigationsample.navigation

import androidx.compose.material3.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation

fun NavGraphBuilder.nestedSampleGraph() {
    navigation(route = "nested_sample", startDestination = "s1") {
        composable("s1") {
            Text("s1")
        }
        composable("s2") {
            Text("s2")
        }
        navigation(route = "nested_sample2", startDestination = "s3") {
            composable("s3") {
                Text("s3")
            }
            composable("s4") {
                Text("s4")
            }
        }
    }
}
