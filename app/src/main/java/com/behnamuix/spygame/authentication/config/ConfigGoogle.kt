package com.behnamuix.spygame.authentication.config

import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import java.security.SecureRandom
import java.util.Base64

fun generateNonce(): String {
    val nonce = ByteArray(32)
    SecureRandom().nextBytes(nonce)
    return Base64.getUrlEncoder().withoutPadding().encodeToString(nonce)
}


fun getSignInWithGoogleOption(): GetSignInWithGoogleOption {
    val webClientId = "1040759260635-2fijhao8mkcojcuee74389br2oentb2r.apps.googleusercontent.com"
    val googleIdOption = GetSignInWithGoogleOption.Builder(webClientId)
        .setNonce(generateNonce())
        .build()
    return googleIdOption
}

