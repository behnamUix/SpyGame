package com.behnamuix.spygame.authentication.repository

import android.content.Context
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.ClearCredentialException
import androidx.credentials.exceptions.GetCredentialException
import com.behnamuix.spygame.model.Profile
import com.behnamuix.spygame.utils.setLog
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import kotlinx.coroutines.coroutineScope

class GoogleAuthRepository(
    private val context: Context,
    private val getGoogleIdOption: GetSignInWithGoogleOption,
    val credentialManager: CredentialManager,
) {
    suspend fun signInWithGoogle(
        onSuccess: (GetCredentialResponse) -> Unit,
        onFailed: (String) -> Unit
    ) {
        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(getGoogleIdOption)
            .build()

        coroutineScope {
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = context,
                )
                onSuccess(result)
            } catch (e: GetCredentialException) {
                onFailed(e.message.toString())
                setLog(e.message.toString())
                // Handle failures
            }
        }
    }

    fun handleCredentialResult(
        result: GetCredentialResponse,
        onSuccess: (Profile) -> Unit,
        onFailed: (String) -> Unit
    ) {
        val credential = result.credential

        if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            try {
                val googleIdTokenCredential = GoogleIdTokenCredential
                    .createFrom(credential.data)
                val googleUser = Profile(
                    idToken = googleIdTokenCredential.idToken,
                    userId = googleIdTokenCredential.id,
                    displayName = googleIdTokenCredential.displayName,
                    email = googleIdTokenCredential.email,
                    profilePictureUri = googleIdTokenCredential.profilePictureUri?.toString()
                )
                setLog(googleUser.displayName.toString())
                onSuccess(googleUser)
            } catch (e: GoogleIdTokenParsingException) {
                onFailed(e.message.toString())
                setLog(e.message.toString())

            }
        } else {
            setLog("Unknown credential type")
        }
    }
    suspend fun isUserAlreadySignedIn(
        onResult: (Profile?) -> Unit
    ) {
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(getGoogleIdOption)
            .build()

        try {
            val result = credentialManager.getCredential(
                request = request,
                context = context
            )

            val credential = result.credential

            if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential =
                    GoogleIdTokenCredential.createFrom(credential.data)

                val profile = Profile(
                    idToken = googleIdTokenCredential.idToken,
                    userId = googleIdTokenCredential.id,
                    displayName = googleIdTokenCredential.displayName,
                    email = googleIdTokenCredential.email,
                    profilePictureUri = googleIdTokenCredential.profilePictureUri?.toString()
                )

                onResult(profile)
            } else {
                onResult(null)
            }


        } catch (e: GetCredentialException) {
            // یعنی کاربر قبلاً لاگین نکرده یا credential موجود نیست
            onResult(null)
        }
    }

    suspend fun signOut() {
        try {
            val clearRequest = ClearCredentialStateRequest()
            credentialManager.clearCredentialState(clearRequest)
        } catch (e: ClearCredentialException) {
            setLog("Couldn't clear user credentials: ${e.localizedMessage}")
        }
    }

}
