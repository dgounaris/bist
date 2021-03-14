package com.gounaris.bist.api.security

import com.gounaris.bist.internal.user.UserDto
import com.gounaris.bist.internal.user.toDto
import com.gounaris.bist.persistence.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

interface UserDetailsSecurityProvider: UserDetailsService {
    fun currentUser(): UserDto?
}

@Component
class UserDetailsSecurityProviderImpl(@Autowired private val userRepository: UserRepository) :
    UserDetailsSecurityProvider {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username) ?: throw UsernameNotFoundException("Username $username matched no user records")

        return object : UserDetails {
            override fun getAuthorities() = emptySet<GrantedAuthority>()
            override fun getUsername(): String = user.username!!
            override fun getPassword(): String = user.password!!
            override fun isAccountNonExpired(): Boolean = true
            override fun isAccountNonLocked(): Boolean = true
            override fun isCredentialsNonExpired(): Boolean = true
            override fun isEnabled(): Boolean = true
        }
    }

    override fun currentUser(): UserDto? = with (SecurityContextHolder.getContext().authentication.principal as String) {
        userRepository.findByUsername(this)?.toDto()
    }
}