package com.khan366kos.serverrationcalculation.config

import com.khan366kos.serverrationcalculation.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class CustomUserDetailsService : UserDetailsService {

    @Autowired
    lateinit var userService: UserService

    override fun loadUserByUsername(login: String): UserDetails {
        val user = userService.findByLogin(login)
        return CustomUserDetails.fromUserEntityToCustomUserDetails(user)
    }
}