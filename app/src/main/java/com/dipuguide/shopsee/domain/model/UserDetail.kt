package com.dipuguide.shopsee.domain.model

data class UserDetail(
    val userId: String = "",
    val userName: String = "",
    val userEmail: String = "",
    val userPassword: String = "",
    val createTimestamp: Long = 0L
)
