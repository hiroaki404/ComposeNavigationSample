package com.example.composenavigationsample.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.composenavigationsample.BirdDetail
import com.example.composenavigationsample.BirdList
import com.example.composenavigationsample.ui.BirdDetailScreen
import com.example.composenavigationsample.ui.BirdListScreen

fun NavGraphBuilder.birdsGraph(
    navController: NavController,
) {
    birdListDestination(
        onListItemClick = { birdId ->
            navController.navigateToDetail(birdId)
        },
    )
    birdDetailDestination(
        onBirdDetailClick = { birdId ->
            navController.navigateToDetail(birdId)
        },
    )
}

fun NavGraphBuilder.birdListDestination(onListItemClick: (Int) -> Unit) {
    composable<BirdList> {
        BirdListScreen(onBirdClick = onListItemClick)
    }
}

fun NavGraphBuilder.birdDetailDestination(onBirdDetailClick: (Int) -> Unit) {
    composable<BirdDetail> { backStackEntry ->
        val birdId = backStackEntry.toRoute<BirdDetail>().id
        BirdDetailScreen(birdId = birdId, onClick = { onBirdDetailClick(birdId) })
    }
}

fun NavGraphBuilder.birdDetailDestination() {
    composable<BirdDetail> { backStackEntry ->
        val birdId = backStackEntry.toRoute<BirdDetail>().id
        BirdDetailScreen(birdId = birdId, onClick = {})
    }
}

fun NavController.navigateToDetail(birdId: Int) {
    navigate(BirdDetail(id = birdId))
}
