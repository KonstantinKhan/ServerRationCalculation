package com.khan366kos.serverrationcalculation.controllers

import com.fasterxml.jackson.annotation.JsonView
import com.khan366kos.serverrationcalculation.models.Ration
import com.khan366kos.serverrationcalculation.models.RationProduct
import com.khan366kos.serverrationcalculation.models.View
import com.khan366kos.serverrationcalculation.repo.ProductsRepository
import com.khan366kos.serverrationcalculation.repo.RationsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.text.SimpleDateFormat
import java.util.*

@Controller
class RationController {

    @Autowired
    lateinit var rationsRepository: RationsRepository

    @Autowired
    lateinit var productsRepository: ProductsRepository

    @RequestMapping("/remove_ration/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    @JsonView(View.REST::class)
    fun removeRation(@PathVariable id: Long) {
        val ration = rationsRepository.findById(id).get()
        rationsRepository.delete(ration)
    }

    // Добавляем продукт в рацион
    @RequestMapping("/add_product/ration/{date}",
            method = [RequestMethod.POST],
            produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    @JsonView(View.REST::class)
    fun addProduct(@PathVariable date: String, @RequestBody rationProduct: RationProduct,
                   @RequestBody user: Long): Ration {
        var ration: Ration
        try {
            // Получаем рацион по переданной дате
            ration = rationsRepository.findByDate(SimpleDateFormat("yyyy-MM-dd").parse(date), user)
        } catch (e: EmptyResultDataAccessException) {
            // Создаем пустой рацион для добавления в него выбранного продукта.
            ration = Ration(0, Date(), 0.0, 0.0, 0.0, 0.0, null)
        }
        // Добавляем продукт в рацион.
        ration.addProduct(rationProduct.product, rationProduct.eating)
        // Сохраняем рацион в базу
        rationsRepository.save(ration)
        ration = rationsRepository.findByDate(SimpleDateFormat("yyyy-MM-dd").parse(date), user)
        rationsRepository.save(ration)
        // Получаем рацион из базы
        return rationsRepository.findByDate(SimpleDateFormat("yyyy-MM-dd").parse(date), user)
    }

    // Удаление продукта из рациона
    @RequestMapping("/delete_product/ration/{date}",
            method = [RequestMethod.PATCH],
            produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    @JsonView(View.REST::class)
    fun deleteProduct(@PathVariable date: String, @RequestBody id: Long, @RequestBody user: Long): Ration {
        val ration = rationsRepository.findByDate(SimpleDateFormat("yyyy-MM-dd").parse(date), user)
        ration.deleteProduct(id)
        ration.update()
        rationsRepository.save(ration)
        return rationsRepository.findByDate(SimpleDateFormat("yyyy-MM-dd").parse(date), user)
    }

    @RequestMapping("update/ration/{date}", method = [RequestMethod.PATCH], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    @JsonView(View.REST::class)
    fun update(@PathVariable date: String, @RequestBody rationProduct: RationProduct,
               @RequestBody user: Long): Ration {
        val ration = rationsRepository.findByDate(SimpleDateFormat("yyyy-MM-dd").parse(date), user)
        ration.ration_product.forEach { value ->
            if (value.rationProductId == rationProduct.rationProductId) {
                value.weight = rationProduct.weight
            }
        }
        ration.update()
        rationsRepository.save(ration)

        return rationsRepository.findByDate(SimpleDateFormat("yyyy-MM-dd").parse(date), user)
    }

    @RequestMapping("/ration/{date}",
            method = [RequestMethod.POST], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    @JsonView(View.REST::class)
    fun getRationJSON(@PathVariable date: String, @RequestBody user: Long): Ration? {

        return try {
            rationsRepository.findByDate(SimpleDateFormat("yyyy-MM-dd").parse(date), user)
        } catch (e: EmptyResultDataAccessException) {
            null
        }
    }

    @RequestMapping("/clear_ration/{date}",
            method = [RequestMethod.PATCH],
            produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    @JsonView(View.REST::class)
    fun clearRation(@PathVariable date: String, @RequestBody user: Long): Ration {
        val ration = rationsRepository.findByDate(SimpleDateFormat("yyyy-MM-dd").parse(date), user)
        ration.clear()
        ration.update()
        rationsRepository.save(ration)
        return rationsRepository.findByDate(SimpleDateFormat("yyyy-MM-dd").parse(date), user)
    }

    /*@RequestMapping("/ration", method = [RequestMethod.POST], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun addProduct(@RequestBody ration: Ration) {

        try {
//            val r = repository.findByDate(ration.date)
//            ration.rationId = r.rationId

            println("Есть рацион")
//            println(ration.products)
//            println(ration.products.last().name)
//            val p = ration.products[1]
//            val p2: Product = r.products[1]
//            println(r)
            *//* println(r.products.first().productsComposition?.get(0)?.rationId)
             println(r.products.first().productsComposition?.get(1)?.rationId)*//*
//            println(p2)
//            r.products.add(p)
//            println(r.products.last().name)
//            println(r.products)
//            r.products.add(ration.products.last())
//            println(ration.products.last())
//            repository.save(ration)
        } catch (e: EmptyResultDataAccessException) {
            println("Рациона нет, создаем новый")
            *//*val r = Ration(ration.rationId, ration.date, ration.calories, ration.proteins, ration.fats,
                    ration.carbohydrates, mutableListOf(), mutableListOf())
            repository.save(r)
            r.products.add(ration.products.last())
            repository.save(r)*//*
//            repository.save(ration)
        }
    }*/
}