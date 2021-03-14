package com.gounaris.bist.internal.user.services

import com.gounaris.bist.api.controllers.user.models.UserRegisterRequestBody
import com.gounaris.bist.internal.user.UserDto
import com.gounaris.bist.persistence.user.User
import com.gounaris.bist.persistence.user.UserRepository
import com.gounaris.bist.api.security.bCryptPasswordEncoder
import com.gounaris.bist.internal.user.toDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.Exception

interface UserRegistrationService {
    fun register(command: UserRegisterCommand): UserDto

    data class UserRegisterCommand(val username: String, val password: String, val email: String)
}

@Service
class UserRegistrationServiceImpl(@Autowired private val userRepository: UserRepository) : UserRegistrationService {
    @Transactional
    override fun register(command: UserRegistrationService.UserRegisterCommand): UserDto {
        userRepository.findByUsername(command.username)?.let {
            throw IllegalArgumentException("User with username ${command.username} already exists")
        }

        return userRepository.save(
            User(null, command.username, bCryptPasswordEncoder.encode(command.password), command.email)
        ).toDto()
    }
}