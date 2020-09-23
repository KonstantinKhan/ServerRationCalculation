package com.khan366kos.serverrationcalculation.repo

import com.khan366kos.serverrationcalculation.models.User
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.userName LIKE :login")
    fun findByLogin(login: String): User
}