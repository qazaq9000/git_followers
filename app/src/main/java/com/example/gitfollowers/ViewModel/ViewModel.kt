package com.example.gitfollowers.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitfollowers.Model.Retrofit.GitHubUser
import com.example.gitfollowers.Model.Retrofit.GithubFollower
import com.example.gitfollowers.Model.Retrofit.RetrofitClient
import com.example.gitfollowers.Model.Room.UserDAO
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AppViewModel(private val userDAO: UserDAO) : ViewModel() {

    val database = MutableStateFlow<List<GitHubUser>>(emptyList())
    private val userFromDB = MutableStateFlow<GitHubUser?>(null)

    private val _login = MutableStateFlow<GitHubUser?>(null)
    val login: StateFlow<GitHubUser?> = _login.asStateFlow()

    private val _follower = MutableStateFlow<List<GithubFollower>>(emptyList())
    val follower: StateFlow<List<GithubFollower>> = _follower.asStateFlow()

    private val errorHandler = CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace()}

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()


    fun getUserFollowers(login: String?) {
        if (login.isNullOrBlank()) {
            _error.value = "Invalid username"
            return
        }
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            try {
                _isLoading.value = true
                val followers = RetrofitClient.apiService.getUserFollowers(login) ?: emptyList()
                withContext(Dispatchers.Main) {
                    _follower.value = followers
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _error.value = e.message
                _isLoading.value = false
            }
        }
    }

    fun getUser(login: String?) {
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
                val username = RetrofitClient.apiService.getUser(login)
            withContext(Dispatchers.Main) {
                _login.value = username
                getUserByLogin(login ?: "")

            }

        }
    }

    fun addUser(login: GitHubUser){
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            userDAO.add(login)
            getAll()
        }
    }

    fun deleteUser(login: GitHubUser){
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            userDAO.delete(login)
            getAll()
        }
    }

    fun getAll() {
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            val users = userDAO.getAllUsers() ?: emptyList()
               withContext(Dispatchers.Main) {
                   database.value = users
            }
        }
    }

    fun getUserByLogin(login: String) {
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            val user = userDAO.getUserByLogin(login)
            withContext(Dispatchers.Main) {
                userFromDB.value = user
            }
        }
    }
}
