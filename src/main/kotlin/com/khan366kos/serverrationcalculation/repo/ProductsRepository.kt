package com.khan366kos.serverrationcalculation.repo

import com.khan366kos.serverrationcalculation.models.Product
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface ProductsRepository : CrudRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE (p.user.userId = :user OR p.verified = true) AND p.name LIKE %:name%")
    fun findByName(@Param("name") name: String, @Param("user") user: Long): List<Product>
}
