package com.khan366kos.serverrationcalculation.models

import javax.persistence.*

@Entity
@Table(name = "roles")
data class Role(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val roleId: Int,
        var nameRole: String
)