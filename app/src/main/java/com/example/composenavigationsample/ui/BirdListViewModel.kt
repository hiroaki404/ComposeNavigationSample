package com.example.composenavigationsample.ui

import androidx.lifecycle.ViewModel
import com.example.composenavigationsample.birdList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BirdListViewModel @Inject constructor() : ViewModel() {
    val birds = birdList
} 
