package com.khan366kos.serverrationcalculation.models

import com.fasterxml.jackson.annotation.JsonView
import javax.persistence.*

@Entity
@Table(name = "dishes_template")
data class DishTemplate(

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @JsonView(View.REST::class)
        val dishTemplateId: Long,

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

        @JsonView(View.REST::class)
        var weightRaw: Int,

        @JsonView(View.REST::class)
        var weightCooked: Int,

        @ManyToOne
        @JoinColumn(name = "userId")
        var user: User?,

        @OneToMany(mappedBy = "dishTemplate",
                cascade = [CascadeType.ALL, CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH],
                orphanRemoval = true)
        @JsonView(View.REST::class)
        val dishTemplate_product: MutableList<DishTemplateProduct> = mutableListOf(),

        @JsonView(View.REST::class)
        var verified: Boolean = false
)