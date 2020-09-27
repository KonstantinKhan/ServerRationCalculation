package com.khan366kos.serverrationcalculation.repo

import com.khan366kos.serverrationcalculation.models.Product
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface ProductsRepository : CrudRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE (p.user.userName = :user OR p.verified = true) AND p.name LIKE %:name%")
    fun findByName(@Param("name") name: String, @Param("user") user: String): List<Product>

    @Query("SELECT p FROM Product p WHERE p.user.userName = :userLogin")
    fun findAllProductsUser(@Param("userLogin") userLogin: String): List<Product>
}
