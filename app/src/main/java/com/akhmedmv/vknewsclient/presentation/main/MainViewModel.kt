package com.akhmedmv.vknewsclient.presentation.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthenticationResult

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _authState = MutableLiveData<AuthState>(AuthState.Initial)
    val authState: LiveData<AuthState> = _authState

    init {
        val storage = VKPreferencesKeyValueStorage(application)
        val token = VKAccessToken.restore(storage)
        val loggedIn = token != null && token.isValid
        Log.d("MainViewModel", "Token: ${token?.accessToken}")
        println("Token: ${token?.accessToken}")
        _authState.value = if (loggedIn ) AuthState.Authorized else AuthState.NotAuthorized
    }

    fun performAuthResult(result: VKAuthenticationResult) {
        when (result) {
            is VKAuthenticationResult.Failed -> {
                var errorMessage =
                    Log.e("VKAuth", "Authentication failed: ")
                _authState.value = AuthState.NotAuthorized
            }

            is VKAuthenticationResult.Success -> {
                Log.d("VKAuth", "Authentication success")
                _authState.value = AuthState.Authorized
            }

            else -> {
                Log.w("VKAuth", "Unknown authentication result: $result")
                _authState.value = AuthState.NotAuthorized
            }
        }
    }

}