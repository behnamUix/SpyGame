package com.behnamuix.spy.model

import android.net.Uri

data class Profile(
    val uid: String="",
    val name: String?="",
    val email: String?="",
    val photoUrl: Uri?=null,
    val isEmailVerified: Boolean=false
)
