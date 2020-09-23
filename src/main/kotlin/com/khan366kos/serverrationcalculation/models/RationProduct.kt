package com.khan366kos.serverrationcalculation.models

import com.fasterxml.jackson.annotation.JsonView
import javax.persistence.*

@Entity
@Table(name = "ration_product")
data class RationProduct(

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @JsonView(View.REST::class)
        val rationProductId: Long,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "product_id")
        @JsonView(View.REST::class)
        val product: Product,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "ration_id")
        val ration: Ration?,

        @JsonView(View.REST::class)
        val eating: String,

        @JsonView(View.REST::class)
        var weight: Int
)
