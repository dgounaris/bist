package com.gounaris.bist.api.controllers.user

import com.gounaris.bist.api.controllers.user.models.UserLoginRequestBody
import com.gounaris.bist.api.controllers.user.models.UserRegisterRequestBody
import com.gounaris.bist.api.security.UserDetailsSecurityProvider
import com.gounaris.bist.internal.user.services.UserDeletionService
import com.gounaris.bist.internal.user.services.UserRegistrationService
import com.gounaris.bist.internal.user.withMaskedPassword
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    @Autowired private val userRegistrationService: UserRegistrationService,
    @Autowired private val userDeletionService: UserDeletionService,
    @Autowired private val userDetailsSecurityProvider: UserDetailsSecurityProvider
) {
    @PostMapping("/login")
    fun login(@RequestBody user: UserLoginRequestBody) {
        // implemented by the authentication filter
    }

    @PostMapping("/register")
    fun register(@RequestBody user: UserRegisterRequestBody) =
        userRegistrationService.register(
            UserRegistrationService.UserRegisterCommand(
            user.username, user.password, user.email
        )).withMaskedPassword()

    @GetMapping("/me")
    fun whoami() = userDetailsSecurityProvider.currentUser()?.withMaskedPassword()

    @DeleteMapping("/{username}")
    fun delete(@PathVariable username: String) =
        userDeletionService.delete(
            UserDeletionService.UserDeleteCommand(
            username
        )).withMaskedPassword()
}