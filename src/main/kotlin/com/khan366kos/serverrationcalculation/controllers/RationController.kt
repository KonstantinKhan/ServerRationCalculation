package com.khan366kos.serverrationcalculation.controllers

import com.fasterxml.jackson.annotation.JsonView
import com.fasterxml.jackson.databind.ObjectMapper
import com.khan366kos.serverrationcalculation.config.jwt.JwtProvider
import com.khan366kos.serverrationcalculation.models.*
import com.khan366kos.serverrationcalculation.repo.DishesRepository
import com.khan366kos.serverrationcalculation.repo.ProductsRepository
import com.khan366kos.serverrationcalculation.repo.RationsRepository
import com.khan366kos.serverrationcalculation.repo.UserRepository
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

    @Autowired
    lateinit var dishesRepository: DishesRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var jwtProvider: JwtProvider

    @RequestMapping("/ration/{date}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    @JsonView(View.REST::class)
    fun getRationJSON(@PathVariable date: String): Ration? {
        return try {
            rationsRepository.findByDate(SimpleDateFormat("yyyy-MM-dd").parse(date), jwtProvider.login)
        } catch (e: EmptyResultDataAccessException) {
            null
        }
    }

    @RequestMapping("/")
    @ResponseBody
    fun hello(): String {
        return "Hello!"
    }

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
    fun addProduct(@PathVariable date: String,
                   @RequestBody rationProduct: RationProduct,
                   @RequestHeader("Authorization") token: String): Ration {
        var ration: Ration
        val user = userRepository.findByLogin(jwtProvider.getLoginFromToken(token.substring(7)))
        ration = try {
            // Получаем рацион по переданной дате
            rationsRepository.findByDate(SimpleDateFormat("yyyy-MM-dd").parse(date), user.userName)
        } catch (e: EmptyResultDataAccessException) {
            // Создаем пустой рацион для добавления в него выбранного продукта.
            Ration(0, Date(), 0.0, 0.0, 0.0, 0.0, user)
        }
        // Добавляем продукт в рацион.
        ration.addProduct(rationProduct.product, rationProduct.eating)
        // Сохраняем рацион в базу
        rationsRepository.save(ration)
        ration = rationsRepository.findByDate(SimpleDateFormat("yyyy-MM-dd").parse(date), user.userName)
        rationsRepository.save(ration)
        // Получаем рацион из базы
        return rationsRepository.findByDate(SimpleDateFormat("yyyy-MM-dd").parse(date), user.userName)
    }

    // Метод для добавления блюда в рацион
    @RequestMapping("/add_dish/ration/{date}",
            method = [RequestMethod.POST],
            produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    @JsonView(View.REST::class)
    fun addDish(@PathVariable date: String,
                @RequestBody rationDish: RationDish,
                @RequestHeader("Authorization") token: String): Ration {
        var ration: Ration
        val user = userRepository.findByLogin(jwtProvider.getLoginFromToken(token.substring(7)))
        ration = try {
            // Получаем рацион по переданной дате
            rationsRepository.findByDate(SimpleDateFormat("yyyy-MM-dd").parse(date), user.userName)
        } catch (e: EmptyResultDataAccessException) {
            // Создаем пустой рацион для добавления в него выбранного продукта.
            Ration(0, Date(), 0.0, 0.0, 0.0, 0.0, user)
        }

        var d: Dish = rationDish.dish

        rationDish.dish.user = user

        rationDish.dish.dish_product.forEach {
            it.dish = d
        }

        // Добавляем блюдо в базу
        dishesRepository.save(rationDish.dish)

        // Добавляем блюдо в рацион
        ration.addDish(rationDish.dish, rationDish.eating)
        // Сохраняем рацион в базу
        rationsRepository.save(ration)
        ration = rationsRepository.findByDate(SimpleDateFormat("yyyy-MM-dd").parse(date), user.userName)
        rationsRepository.save(ration)
        // Получаем рацион из базы
        return rationsRepository.findByDate(SimpleDateFormat("yyyy-MM-dd").parse(date), user.userName)
    }

    // Метод для удаления продукта из рациона
    @RequestMapping("/delete_product/ration/{date}",
            method = [RequestMethod.PATCH],
            produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    @JsonView(View.REST::class)
    fun deleteProduct(@PathVariable date: String, @RequestBody id: Long,
                      @RequestHeader("Authorization") token: String): Ration {
        val ration = rationsRepository.findByDate(SimpleDateFormat("yyyy-MM-dd").parse(date),
                jwtProvider.getLoginFromToken(token.substring(7)))
        ration.deleteProduct(id)
        ration.update()
        rationsRepository.save(ration)
        return rationsRepository.findByDate(SimpleDateFormat("yyyy-MM-dd").parse(date),
                jwtProvider.getLoginFromToken(token.substring(7)))
    }

    // Метод для удаления блюда из рациона
    @RequestMapping("/delete_dish/ration/{date}",
            method = [RequestMethod.PATCH],
            produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    @JsonView(View.REST::class)
    fun deleteDish(@PathVariable date: String,
                   @RequestBody id: Long,
                   @RequestHeader("Authorization") token: String): Ration {
        val ration = rationsRepository.findByDate(SimpleDateFormat("yyyy-MM-dd").parse(date),
                jwtProvider.getLoginFromToken(token.substring(7)))
        ration.deleteDish(id)
        ration.update()
        rationsRepository.save(ration)
        return rationsRepository.findByDate(SimpleDateFormat("yyyy-MM-dd").parse(date),
                jwtProvider.getLoginFromToken(token.substring(7)))
    }

    @RequestMapping("update/ration_product/{date}",
            method = [RequestMethod.PATCH],
            produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    @JsonView(View.REST::class)
    fun updateByProduct(@PathVariable date: String, @RequestBody rationProduct: RationProduct): Ration {

        val ration = rationsRepository.findByDate(SimpleDateFormat("yyyy-MM-dd").parse(date), jwtProvider.login)
        ration.ration_product.forEach { value ->
            if (value.rationProductId == rationProduct.rationProductId) {
                value.weight = rationProduct.weight
            }
        }
        ration.update()
        rationsRepository.save(ration)

        return rationsRepository.findByDate(SimpleDateFormat("yyyy-MM-dd").parse(date), jwtProvider.login)
    }

    @RequestMapping("update/ration_dish/{date}",
            method = [RequestMethod.PATCH],
            produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    @JsonView(View.REST::class)
    fun updateByDish(@PathVariable date: String,
                     @RequestBody rationDish: RationDish,
                     @RequestHeader("Authorization") token: String
    ): Ration {

        val ration = rationsRepository.findByDate(SimpleDateFormat("yyyy-MM-dd").parse(date),
                jwtProvider.getLoginFromToken(token.substring(7)))

        ration.ration_dish.forEach { value ->
            if (value.rationDishId == rationDish.rationDishId) {
                value.weight = rationDish.weight
            }
        }
        ration.update()
        rationsRepository.save(ration)

        return rationsRepository.findByDate(SimpleDateFormat("yyyy-MM-dd").parse(date),
                jwtProvider.getLoginFromToken(token.substring(7)))
    }

    @RequestMapping("/clear_ration/{date}",
            method = [RequestMethod.PATCH],
            produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    @JsonView(View.REST::class)
    fun clearRation(@PathVariable date: String, @RequestBody user: Long): Ration {
        val ration = rationsRepository.findByDate(SimpleDateFormat("yyyy-MM-dd").parse(date), jwtProvider.login)
        ration.clear()
        ration.update()
        rationsRepository.save(ration)
        return rationsRepository.findByDate(SimpleDateFormat("yyyy-MM-dd").parse(date), jwtProvider.login)
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