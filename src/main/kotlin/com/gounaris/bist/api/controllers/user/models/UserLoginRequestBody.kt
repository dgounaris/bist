package com.gounaris.bist.api.controllers.user.models

data class UserLoginRequestBody(
    val username: String,
    val password: String
)