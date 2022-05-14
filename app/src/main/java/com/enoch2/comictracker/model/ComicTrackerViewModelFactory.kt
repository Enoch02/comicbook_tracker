package com.enoch2.comictracker.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/*
class ComicTrackerViewModelFactory(private val context: Context): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Context::class.java)
            .newInstance(context)
    }
}*/

class ComicTrackerViewModelFactory (private val context: Context) :
        ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>):
            T = ComicTrackerViewModel(context) as T
    }
