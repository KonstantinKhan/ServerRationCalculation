package com.khan366kos.serverrationcalculation.models

import com.fasterxml.jackson.annotation.JsonView
import javax.persistence.*

@Entity
@Table(name = "dishTemplate_product")
class DishTemplateProduct(

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @JsonView(View.REST::class)
        val dishTemplateProductId: Long,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "product_id")
        @JsonView(View.REST::class)
        val product: Product,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "dish_id")
        var dishTemplate: DishTemplate?,

        @JsonView(View.REST::class)
        var weight: Int
)
