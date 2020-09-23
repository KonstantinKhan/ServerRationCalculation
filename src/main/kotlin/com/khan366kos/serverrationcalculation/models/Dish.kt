package com.khan366kos.serverrationcalculation.models

import com.fasterxml.jackson.annotation.JsonView
import javax.persistence.*

/*@Entity
@Table(name = "dishes")*/
data class Dish(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @JsonView(View.REST::class)
        val dishId: Long,

        @JsonView(View.REST::class)
        var name: String

        /*@JsonView(View.REST::class)
        var calories: Double,

        @JsonView(View.REST::class)
        var proteins: Double,

        @JsonView(View.REST::class)
        var fats: Double,

        @JsonView(View.REST::class)
        var carbohydrates: Double,

        @JsonView(View.REST::class)
        var weightRaw: Int,

        @JsonView(View.REST::class)
        var weightCooked: Int,*/

       /* @ManyToMany(mappedBy = "dishes")
        var dishesComposition: MutableList<Ration>?,*/

      /*  @OneToMany(mappedBy = "dish")
        @JsonView(View.REST::class)
        var dishComposition: MutableList<DishComposition>,
*/
       /* @JsonView(View.REST::class)
        var verified: Boolean = false*/
)