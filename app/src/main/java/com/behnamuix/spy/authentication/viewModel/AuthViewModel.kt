package com.behnamuix.spy.authentication.viewModel

import android.content.Context
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.exceptions.ClearCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.behnamuix.spy.authentication.repository.AuthRepository
import com.behnamuix.spy.model.Profile
import com.behnamuix.spy.utils.setLog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authReo: AuthRepository,
    private val cred: CredentialManager
) :
    ViewModel() {

    var _currentUserProfile = MutableStateFlow<Profile?>(null)
    var currentUserProfile: StateFlow<Profile?> = _currentUserProfile.asStateFlow()


    fun signInWithGoogle(
        context: Context,
        onSuccess: () -> Unit,
        onFailed: (String) -> Unit
    ) {
        viewModelScope.launch {

            authReo.loginWithGoogle(context, onSuccess, onFailed)
        }
    }

    fun updateCurrentUser() {
        val user = authReo.profile.currentUser
        _currentUserProfile.value = if (user != null) {
            Profile(
                uid = user.uid,
                name = user.displayName ?: "کاربر بدون نام",
                email = user.email,
                photoUrl = user.photoUrl,
                isEmailVerified = user.isEmailVerified
            )
        } else {
            setLog("user is null")
            null
        }
    }

    fun signOut() {
        // Firebase sign out
        authReo.profile.signOut()

        // When a user signs out, clear the current user credential state from all credential providers.
        viewModelScope.launch {
            try {
                val clearRequest = ClearCredentialStateRequest()
                cred.clearCredentialState(clearRequest)
                updateCurrentUser()
            } catch (e: ClearCredentialException) {
                setLog("Couldn't clear user credentials: ${e.localizedMessage}")
            }
        }
    }

    fun signedInCheck()= authReo.profile.currentUser !== null

}