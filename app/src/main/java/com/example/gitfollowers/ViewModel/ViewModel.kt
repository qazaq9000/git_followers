package com.example.gitfollowers.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitfollowers.Model.Retrofit.GitHubUser
import com.example.gitfollowers.Model.Retrofit.GithubFollower
import com.example.gitfollowers.Model.Retrofit.RetrofitClient
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AppViewModel : ViewModel() {
    private val _login = MutableStateFlow<GitHubUser?>(null)
    val login: StateFlow<GitHubUser?> = _login.asStateFlow()

    private val _follower = MutableStateFlow<List<GithubFollower>>(emptyList())
    val follower: StateFlow<List<GithubFollower>> = _follower.asStateFlow()

    private val errorHandler = CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace()}

    fun getUserFollowers(login: String?) {
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
                val follower = RetrofitClient.apiService.getUserFollowers(login) ?: emptyList()
            withContext(Dispatchers.Main){
                _follower.value = follower
            }
        }
    }

    fun getUser(login: String?) {
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
                val username = RetrofitClient.apiService.getUser(login)
            withContext(Dispatchers.Main) {
                _login.value = username
            }
        }
    }
}