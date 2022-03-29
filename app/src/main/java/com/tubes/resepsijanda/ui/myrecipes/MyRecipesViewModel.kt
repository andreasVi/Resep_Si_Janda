package com.tubes.resepsijanda.ui.myrecipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyRecipesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is My Recipes Fragment"
    }
    val text: LiveData<String> = _text
}