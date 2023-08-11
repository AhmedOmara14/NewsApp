package com.omaradev.newsapp.data.repository

import com.omaradev.newsapp.data.remote.ApiService
import com.omaradev.newsapp.domain.repository.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(val api: ApiService) : Repository,
    AppBaseRemoteRepository() {
        
}