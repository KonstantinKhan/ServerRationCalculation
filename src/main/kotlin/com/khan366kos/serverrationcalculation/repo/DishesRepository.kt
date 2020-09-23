package com.khan366kos.serverrationcalculation.repo

import com.khan366kos.serverrationcalculation.models.Dish
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

/*interface DishesRepository : CrudRepository<Dish, Long> {

    @Query("SELECT d FROM Dish d WHERE d.name LIKE %:name%")
    fun findByName(@Param("name") name: String): List<Dish>

}*/
