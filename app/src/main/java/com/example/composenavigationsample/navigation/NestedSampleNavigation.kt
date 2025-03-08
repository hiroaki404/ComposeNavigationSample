package com.example.composenavigationsample.navigation

import androidx.compose.material3.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

fun NavGraphBuilder.nestedSampleGraph() {
    navigation<NestedSampleRoute.NestedGraph>(startDestination = NestedSampleRoute.NestedSampleScreen1) {
        composable<NestedSampleRoute.NestedSampleScreen1> {
            Text("s1")
        }
        composable<NestedSampleRoute.NestedSampleScreen2> {
            Text("s2")
        }
        navigation<NestedSampleRoute.NestedNestedGraph>(startDestination = NestedSampleRoute.NestedSampleScreen3) {
            composable<NestedSampleRoute.NestedSampleScreen3> {
                Text("s3")
            }
            composable<NestedSampleRoute.NestedSampleScreen4> {
                Text("s4")
            }
        }
    }
}

sealed interface NestedSampleRoute {
    @Serializable
    @SerialName("NestedGraph")
    data object NestedGraph : NestedSampleRoute

    @Serializable
    @SerialName("NestedSampleScreen1")
    data object NestedSampleScreen1 : NestedSampleRoute

    @Serializable
    @SerialName("NestedSampleScreen2")
    data object NestedSampleScreen2 : NestedSampleRoute

    @Serializable
    @SerialName("NestedNestedGraph")
    data object NestedNestedGraph : NestedSampleRoute

    @Serializable
    @SerialName("NestedSampleScreen3")
    data object NestedSampleScreen3 : NestedSampleRoute

    @Serializable
    @SerialName("NestedSampleScreen4")
    data object NestedSampleScreen4 : NestedSampleRoute
}
