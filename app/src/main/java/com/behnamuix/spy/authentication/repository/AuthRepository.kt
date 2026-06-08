package com.behnamuix.spy.authentication.repository

import android.content.Context
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.ClearCredentialException
import com.behnamuix.spy.utils.setLog
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class AuthRepository(
    private val getGoogleIdOption: GetGoogleIdOption,
    private val auth: FirebaseAuth,
    val credentialManager: CredentialManager,
) {
    val profile = auth

    suspend fun loginWithGoogle(
        context: Context,
        onSuccess: () -> Unit,
        onFailed: (String) -> Unit
    ) {
        try {
            val request = GetCredentialRequest.Builder()
                .addCredentialOption(getGoogleIdOption)
                .build()
            val result = credentialManager.getCredential(context = context, request = request)
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(result.credential.data)
            val credential = GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)
            profile.signInWithCredential(credential)
                .await()
            onSuccess()
        } catch (e: Exception) {
            onFailed(e.localizedMessage ?: "خطا در احراز هویت")
        }
    }

    suspend fun signOut() {
        // Firebase sign out
        auth.signOut()
        try {
            val clearRequest = ClearCredentialStateRequest()
            credentialManager.clearCredentialState(clearRequest)
        } catch (e: ClearCredentialException) {
            setLog("Couldn't clear user credentials: ${e.localizedMessage}")
        }
    }


}