package com.example.composenavigationsample.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun BirdListScreen(
    viewModel: BirdListViewModel = hiltViewModel(),
    onBirdClick: (Int) -> Unit,
) {
    LazyColumn {
        items(viewModel.birds) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onBirdClick(it.id)
                    }
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(it.name)
            }
            HorizontalDivider()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BirdListScreenPreview() {
    BirdListScreen(
        onBirdClick = {},
    )
}
