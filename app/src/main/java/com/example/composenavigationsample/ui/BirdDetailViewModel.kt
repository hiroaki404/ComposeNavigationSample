package com.example.composenavigationsample.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.example.composenavigationsample.BirdDetail
import com.example.composenavigationsample.birdList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BirdDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val birdId = savedStateHandle.toRoute<BirdDetail>().id
    val bird = birdList.find { it.id == birdId }
}
