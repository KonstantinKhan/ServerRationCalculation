package com.khan366kos.serverrationcalculation.models

import com.fasterxml.jackson.annotation.JsonView
import javax.persistence.*

@Entity
@Table(name = "ration_dish")
class RationDish(

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @JsonView(View.REST::class)
        val rationDishId: Long,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "dish_id")
        @JsonView(View.REST::class)
        val dish: Dish,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "ration_id")
        val ration: Ration,

        @JsonView(View.REST::class)
        val eating: String,

        @JsonView(View.REST::class)
        var weight: Int

)