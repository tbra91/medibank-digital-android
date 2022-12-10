package com.medibank.shop.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import com.medibank.shop.data.ArticleRepository
import com.medibank.shop.data.NewsDatabase

class SavesViewModel(application: Application) : AndroidViewModel(application) {

    val articles =
        ArticleRepository(NewsDatabase.getInstance(application).articleDao).getAll().asLiveData()
}
