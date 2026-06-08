package com.behnamuix.spy.authentication.config

import com.behnamuix.spy.utils.setLog
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.firebase.auth.FirebaseAuth


fun getGoogleIdOption(): GetGoogleIdOption {
    val googleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId(
            "698021296400-l9shtgvvahqav9gols0pim6gpgisnl4q.apps.googleusercontent.com"
        )
        .setAutoSelectEnabled(false)
        .build()
    setLog("gio created!")
    return googleIdOption
}

