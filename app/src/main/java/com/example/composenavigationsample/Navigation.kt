package com.example.composenavigationsample

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.composenavigationsample.ui.BirdDetailScreen
import com.example.composenavigationsample.ui.BirdListScreen

fun NavGraphBuilder.birdsGraph(
    onListItemClick: (Int) -> Unit,
    onBirdDetailClick: () -> Unit,
) {
    composable<BirdList> {
        BirdListScreen(onBirdClick = onListItemClick)
    }
    composable<BirdDetail> { backStackEntry ->
        val birdId = backStackEntry.toRoute<BirdDetail>().id
        BirdDetailScreen(birdId = birdId, onClick = onBirdDetailClick)
    }
}
