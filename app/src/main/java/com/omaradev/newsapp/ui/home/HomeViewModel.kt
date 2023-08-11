package com.omaradev.newsapp.ui.home

import androidx.lifecycle.ViewModel
import com.omaradev.newsapp.domain.model.news.Article
import com.omaradev.newsapp.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
  val newsItemsList = mutableListOf<Article>()

}
