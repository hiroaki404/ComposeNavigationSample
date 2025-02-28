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

// Don't use this pattern
// @AssistedFactory
// interface BirdDetailViewModel2Factory {
//    fun create(
//        @Assisted("birdId") birdId: Int,
//    ): BirdDetailViewModel2
// }
//
// @HiltViewModel(assistedFactory = BirdDetailViewModel2Factory::class)
// class BirdDetailViewModel2 @AssistedInject constructor(
//    @Assisted("birdId") private val birdId: Int,
// ) : ViewModel() {
//    val bird = birdList.find { it.id == birdId }
// }
