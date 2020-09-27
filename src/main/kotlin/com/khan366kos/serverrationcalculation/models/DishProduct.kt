package com.khan366kos.serverrationcalculation.models

import com.fasterxml.jackson.annotation.JsonView
import javax.persistence.*

@Entity
@Table(name = "dish_product")
class DishProduct(

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @JsonView(View.REST::class)
        val dishProductId: Long,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "product_id")
        @JsonView(View.REST::class)
        val product: Product,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "dish_id")
        val dish: Dish?,

        @JsonView(View.REST::class)
        val eating: String,

        @JsonView(View.REST::class)
        var weight: Int
)