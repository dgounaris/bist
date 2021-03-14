package com.gounaris.bist.internal.user.services

import com.gounaris.bist.internal.user.UserDto
import com.gounaris.bist.internal.user.toDto
import com.gounaris.bist.persistence.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.Exception

interface UserDeletionService {
    fun delete(command: UserDeleteCommand): UserDto

    data class UserDeleteCommand(val username: String)
}

@Service
class UserDeletionServiceImpl(@Autowired private val userRepository: UserRepository) : UserDeletionService {
    @Transactional
    override fun delete(command: UserDeletionService.UserDeleteCommand): UserDto {
        val user = userRepository.findByUsername(command.username)
            ?: throw NoSuchElementException("No user with username ${command.username} found.")

        userRepository.delete(user)

        return user.toDto()
    }
}