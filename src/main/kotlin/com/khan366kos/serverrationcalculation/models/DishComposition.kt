package com.khan366kos.serverrationcalculation.models

import com.fasterxml.jackson.annotation.JsonView
import javax.persistence.*

data class DishComposition(

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @JsonView(View.REST::class)
        val dishCompositionId: Long

       /* @ManyToOne
        @JoinColumn(name = "product_id")
        @JsonView(View.REST::class)
        val product: Product,

        @ManyToOne
        @JoinColumn(name = "dish_id")
        val dish: Dish?,*/

     /*   @JsonView(View.REST::class)
        var weightRaw: Int,

        @JsonView(View.REST::class)
        var cookedProductWeight: Int*/
)