package com.behnamuix.spygame.model

data class Profile(
    val idToken: String,
    val userId: String,
    val displayName: String?,
    val email: String?,
    val profilePictureUri: String?
)
