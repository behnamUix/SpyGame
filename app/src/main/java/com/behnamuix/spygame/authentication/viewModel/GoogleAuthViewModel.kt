package com.behnamuix.spygame.authentication.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.behnamuix.spygame.authentication.repository.GoogleAuthRepository
import com.behnamuix.spygame.model.Profile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GoogleAuthViewModel(
    private val authReo: GoogleAuthRepository,
) :
    ViewModel() {

    var _currentUserProfile = MutableStateFlow<Profile?>(null)
    var currentUserProfile: StateFlow<Profile?> = _currentUserProfile.asStateFlow()


    fun singIn(ok: (Profile) -> Unit, error: (String) -> Unit) {
        viewModelScope.launch {
            authReo.signInWithGoogle({
                authReo.handleCredentialResult(it, { profile ->
                    ok(profile)
                }, { e ->
                    error(e)
                })
            }) {

            }
        }
    }


    fun signOut() {
        // When a user signs out, clear the current user credential state from all credential providers.
        viewModelScope.launch {
            authReo.signOut()
            _currentUserProfile.value = null
        }
    }
    fun checkPreviousLogin(onResult: (Profile?) -> Unit) {
        viewModelScope.launch {
            authReo.isUserAlreadySignedIn {
                onResult(it)
            }
        }
    }
    // چک کن توی GoogleAuthViewModel متد لاگین بی‌صدا اینطوری باشه:



    fun updateProfile(profile: Profile) {
        _currentUserProfile.value = profile

    }


}