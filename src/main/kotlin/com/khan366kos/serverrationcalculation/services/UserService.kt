package com.khan366kos.serverrationcalculation.services

import com.khan366kos.serverrationcalculation.models.Role
import com.khan366kos.serverrationcalculation.models.User
import com.khan366kos.serverrationcalculation.repo.RoleRepository
import com.khan366kos.serverrationcalculation.repo.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var roleRepository: RoleRepository

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    fun saveUser(user: User): User {
        val userRole: Role = roleRepository.findByName("ROLE_USER")
        user.role = userRole
        user.userPassword = passwordEncoder.encode(user.userPassword)
        return userRepository.save(user)
    }

    fun findByLogin(login: String): User {
        return userRepository.findByLogin(login)
    }

    fun findByLoginAndPassword(login: String, password: String): User? {
        val user = findByLogin(login)
        if (user != null) {
            if (passwordEncoder.matches(password, user.userPassword)) {
                return user
            }
        }
        return null
    }
}