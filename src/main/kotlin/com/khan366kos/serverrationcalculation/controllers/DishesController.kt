package com.khan366kos.serverrationcalculation.controllers

import com.fasterxml.jackson.annotation.JsonView
import com.khan366kos.serverrationcalculation.config.jwt.JwtProvider
import com.khan366kos.serverrationcalculation.models.Dish
import com.khan366kos.serverrationcalculation.models.View
import com.khan366kos.serverrationcalculation.repo.DishesRepository
import com.khan366kos.serverrationcalculation.repo.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@Controller
class DishesController {

    @Autowired
    lateinit var dishesRepository: DishesRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var jwtProvider: JwtProvider

    //    @JsonView(View.REST::class)
//    @RequestMapping("/dish", produces = ["application/json"])
//    @ResponseBody
//    fun getDishJSON(): Dish {
//        return repository.findAll().first()
//    }
//
    @JsonView(View.REST::class)
    @RequestMapping("/dishes/all", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun getAllDishJSON(@RequestHeader("Authorization") header: String): List<Dish> {

        println(jwtProvider.getLoginFromToken(header.substring(7)))

        val dishes = dishesRepository.findAll()
        return dishesRepository.findAllDishesUser(jwtProvider.login).toList()
    }

    @RequestMapping("/dish/save", method = [RequestMethod.POST], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun saveDish(@RequestBody dish: Dish, @RequestHeader header: RequestHeader) {

        val user = userRepository.findByLogin(jwtProvider.login)
        dish.user = user
        println(dish.dishId)
        dishesRepository.save(dish)
        dish.dish_product.forEach { it.dish = dish }
        dishesRepository.save(dish)

    }
//
//    @RequestMapping("/dishes/search/{name}", produces = [MediaType.APPLICATION_JSON_VALUE])
//    @ResponseBody
//    @JsonView(View.REST::class)
//    fun getSearchDishes(@PathVariable name: String): List<Dish> {
//        return repository.findByName(name)
//    }
}