package com.gounaris.bist.internal.user

import com.gounaris.bist.persistence.user.User

data class UserDto(
    val id: Long?,
    val username: String?,
    val password: String?,
    val email: String?
)

fun UserDto.withMaskedPassword() = this.copy(password = "{hidden}")

fun User.toDto() = UserDto(
    id = id, username = username, password = password, email = email
)

fun UserDto.toEntity() = User(
    id = id, username = username, password = password, email = email
)