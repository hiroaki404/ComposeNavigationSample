package com.example.composenavigationsample.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composenavigationsample.birdList
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun BirdDetailScreen(
    birdId: Int,
) {
    val bird = birdList.find { it.id == birdId }

    bird?.let {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Text(
                text = it.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = "渡りの種類: ${it.watariType.displayName}",
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 8.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BirdDetailScreenPreview() {
    BirdDetailScreen(
        birdId = 1
    )
}
