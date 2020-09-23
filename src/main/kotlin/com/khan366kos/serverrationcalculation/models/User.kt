package com.khan366kos.serverrationcalculation.models

import com.fasterxml.jackson.annotation.JsonView
import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
        @JsonView(View.REST::class)
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val userId: Long,

        var userName: String,

        var userPassword: String,

        @ManyToOne
        @JoinColumn(name = "roleId")
        var role: Role?
)