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
        val ration_product: MutableList<RationProduct> = mutableListOf(),

        @OneToMany(mappedBy = "ration",
                cascade = [CascadeType.ALL, CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH],
                orphanRemoval = true)
        @JsonView(View.REST::class)
        val ration_dish: MutableList<RationDish> = mutableListOf()
        /*@ManyToMany(fetch = FetchType.LAZY,
                cascade = [
                    CascadeType.REFRESH])
        @JoinTable(name = "ration_product",
                joinColumns = [JoinColumn(name = "ration_id")],
                inverseJoinColumns = [JoinColumn(name = "product_id")])
        @JsonView(View.REST::class)
        var products: MutableList<Product>*/
) {
    // Метод добавления продукта в рацион
    fun addProduct(product: Product, eating: String) {
        ration_product.add(RationProduct(0, product, this, eating, 0))
    }

    // Метод добавления блюда в рацион
    fun addDish(dish: Dish, eating: String) {
        ration_dish.add(RationDish(0, dish, this, eating, 0))
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

    // Метод для удаления блюда из рациона
    fun deleteDish(id: Long) {
        var i = 0
        var remove = false
        ration_dish.forEach { value ->
            if (value.rationDishId == id) {
                i = ration_dish.indexOf(value)
                remove = true
            }
        }
        if (remove) {
            ration_dish.removeAt(i)
        }
    }

    // Метод для обновления параметров рациона
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
        ration_dish.forEach { value ->
            calories += value.dish.calories * value.weight / 100
            proteins += value.dish.proteins * value.weight / 100
            fats += value.dish.fats * value.weight / 100
            carbohydrates += value.dish.carbohydrates * value.weight / 100
        }
    }

    fun clear() {
        ration_product.clear()
    }
}

