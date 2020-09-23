package com.khan366kos.serverrationcalculation.repo

import com.khan366kos.serverrationcalculation.models.Role
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface RoleRepository : CrudRepository<Role, Int> {

    @Query("SELECT r FROM Role r WHERE r.nameRole LIKE :name")
    fun findByName(name: String): Role
}