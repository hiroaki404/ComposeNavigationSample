package com.example.composenavigationsample.navigation

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import androidx.navigation.toRoute
import com.example.composenavigationsample.DialogDestinationSample

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.dialogGraph(navController: NavController) {
    dialog<DialogDestinationSample>(
        dialogProperties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        val birdId = it.toRoute<DialogDestinationSample>().id
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text("Dialog $birdId")
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                Icons.Filled.ArrowBack,
                                contentDescription = "Back",
                            )
                        }
                    },
                )
            },
        ) { innerPadding ->
            Text(modifier = Modifier.padding(innerPadding), text = "Dialog")
        }
    }
}
