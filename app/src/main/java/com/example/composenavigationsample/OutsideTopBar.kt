package com.example.composenavigationsample

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Context.OutsideTopBar(
    isTopBarVisible: Boolean,
    currentBackstackEntryRoute: String?,
    popBackStack: () -> Unit,
    navigateToMain: () -> Unit,
) {
    val height = animateDpAsState(
        targetValue = if (isTopBarVisible) 100.dp else 0.dp,
        label = "",
    )
    AnimatedVisibility(
        modifier = Modifier.heightIn(max = height.value),
        visible = isTopBarVisible,
        enter = fadeIn(animationSpec = tween(700)),
        exit = fadeOut(animationSpec = tween(700)),
    ) {
        if (isTopBarVisible) {
            TopAppBar(
                title = {
                    Text(
                        "${
                            currentBackstackEntryRoute?.removePrefix(
                                "$packageName.",
                            )
                        }",
                    )
                },
                navigationIcon = {
                    if (currentBackstackEntryRoute != BirdList::class.qualifiedName) {
                        IconButton(onClick = popBackStack) {
                            Icon(
                                Icons.Filled.ArrowBack,
                                contentDescription = "Back",
                            )
                        }
                    }
                },
                actions = {
                    TextButton(onClick = navigateToMain) {
                        Text("Go to Main Graph")
                    }
                },
            )
        } else {
            // Do not display content during the top bar disappearance animation
            TopAppBar(title = {})
        }
    }
}
