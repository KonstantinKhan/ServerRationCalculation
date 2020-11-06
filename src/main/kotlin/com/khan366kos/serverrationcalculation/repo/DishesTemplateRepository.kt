package com.khan366kos.serverrationcalculation.repo

import com.khan366kos.serverrationcalculation.models.DishTemplate
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface DishesTemplateRepository : CrudRepository<DishTemplate, Long> {

    @Query("SELECT d FROM DishTemplate d WHERE d.name LIKE %:name%")
    fun findByName(@Param("name") name: String): List<DishTemplate>

    @Query("SELECT d FROM DishTemplate d WHERE d.user.userName = :userLogin")
    fun findAllDishesUser(@Param("userLogin") userLogin: String): List<DishTemplate>
}