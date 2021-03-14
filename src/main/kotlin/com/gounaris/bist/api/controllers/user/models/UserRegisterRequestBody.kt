package com.gounaris.bist.api.controllers.user.models

data class UserRegisterRequestBody(
    val username: String,
    val password: String,
    val email: String
)