package com.khan366kos.serverrationcalculation.models

import com.fasterxml.jackson.annotation.JsonView
import javax.persistence.*

@Entity
@Table(name = "products")
data class Product(

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @JsonView(View.REST::class)
        val productId: Long,

        @JsonView(View.REST::class)
        var name: String,

        @JsonView(View.REST::class)
        var calories: Double,

        @JsonView(View.REST::class)
        var proteins: Double,

        @JsonView(View.REST::class)
        var fats: Double,

        @JsonView(View.REST::class)
        var carbohydrates: Double,

        @OneToMany(mappedBy = "product")
        val ration_product: MutableList<RationProduct> = mutableListOf(),

        @OneToMany(mappedBy = "product")
        val dish_product: MutableList<DishProduct> = mutableListOf(),

        /*@ManyToMany(mappedBy = "products")
        var productsComposition: MutableList<Ration>?,*/

        /*@OneToMany(mappedBy = "product")
         val ration_product: MutableList<RationProduct>,*/

        @JsonView(View.REST::class)
        var verified: Boolean = false,

        @ManyToOne
        @JoinColumn(name = "userId")
        var user: User?
)