package com.khan366kos.serverrationcalculation.models

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonView
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "ration")
data class Ration(

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @JsonView(View.REST::class)
        var rationId: Long,

        @JsonView(View.REST::class)
        @Temporal(TemporalType.DATE)
        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Yekaterinburg")
        var date: Date,

        @JsonView(View.REST::class)
        var calories: Double,

        @JsonView(View.REST::class)
        var proteins: Double,

        @JsonView(View.REST::class)
        var fats: Double,

        @JsonView(View.REST::class)
        var carbohydrates: Double,

        @ManyToOne
        @JoinColumn(name = "userId")
        var user: User?,

        /* @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
         @JoinTable(name = "ration_dish",
                 joinColumns = [JoinColumn(name = "ration_id")],
                 inverseJoinColumns = [JoinColumn(name = "dish_id")])
         @JsonView(View.REST::class)
         var dishes: MutableList<Dish>,*/

        @OneToMany(mappedBy = "ration",
                cascade = [CascadeType.ALL, CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH],
                orphanRemoval = true)
        @JsonView(View.REST::class)
        val ration_product: MutableList<RationProduct> = mutableListOf()
        /*@ManyToMany(fetch = FetchType.LAZY,
                cascade = [
                    CascadeType.REFRESH])
        @JoinTable(name = "ration_product",
                joinColumns = [JoinColumn(name = "ration_id")],
                inverseJoinColumns = [JoinColumn(name = "product_id")])
        @JsonView(View.REST::class)
        var products: MutableList<Product>*/
) {
    // Добавление продукта
    fun addProduct(product: Product, eating: String) {
        ration_product.add(RationProduct(0, product, this, eating, 0))
    }

    // Удаление продукта
    fun deleteProduct(id: Long) {
        var i = 0
        var remove = false
        ration_product.forEach { value ->
            if (value.rationProductId == id) {
                i = ration_product.indexOf(value)
                remove = true
            }
        }
        if (remove) {
            ration_product.removeAt(i)
        }
    }

    // Обновление параметров рациона
    fun update() {
        calories = 0.0
        proteins = 0.0
        fats = 0.0
        carbohydrates = 0.0
        ration_product.forEach { value ->
            calories += value.product.calories * value.weight / 100
            proteins += value.product.proteins * value.weight / 100
            fats += value.product.fats * value.weight / 100
            carbohydrates += value.product.carbohydrates * value.weight / 100
        }
    }

    fun clear() {
        ration_product.clear()
    }
}

