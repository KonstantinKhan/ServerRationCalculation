package com.khan366kos.serverrationcalculation.controllers

import com.fasterxml.jackson.annotation.JsonView
import com.khan366kos.serverrationcalculation.config.jwt.JwtProvider
import com.khan366kos.serverrationcalculation.models.Dish
import com.khan366kos.serverrationcalculation.models.View
import com.khan366kos.serverrationcalculation.repo.DishesRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
class DishesController {

    @Autowired
    lateinit var repository: DishesRepository

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
    @RequestMapping("/dishes/all", produces = ["application/json"])
    @ResponseBody
    fun getAllDishJSON(): List<Dish> {
        val dishes = repository.findAll()
        return repository.findAllDishesUser(jwtProvider.login).toList()
    }
//
//    @RequestMapping("/dishes/search/{name}", produces = [MediaType.APPLICATION_JSON_VALUE])
//    @ResponseBody
//    @JsonView(View.REST::class)
//    fun getSearchDishes(@PathVariable name: String): List<Dish> {
//        return repository.findByName(name)
//    }
}