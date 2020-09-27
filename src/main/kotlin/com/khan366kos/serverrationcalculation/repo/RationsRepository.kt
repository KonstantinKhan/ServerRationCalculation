package com.khan366kos.serverrationcalculation.repo

import com.khan366kos.serverrationcalculation.models.Ration
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.util.*

interface RationsRepository : CrudRepository<Ration, Long> {

    @Query("SELECT r FROM Ration r WHERE r.user.userName = :user and r.date = :date")
    fun findByDate(@Param("date") date: Date, @Param("user") user: String): Ration
}
