package com.khan366kos.serverrationcalculation.controllers

import com.fasterxml.jackson.annotation.JsonView
import com.khan366kos.serverrationcalculation.config.jwt.JwtProvider
import com.khan366kos.serverrationcalculation.models.Dish
import com.khan366kos.serverrationcalculation.models.DishTemplate
import com.khan366kos.serverrationcalculation.models.View
import com.khan366kos.serverrationcalculation.repo.DishesTemplateRepository
import com.khan366kos.serverrationcalculation.repo.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
class DishesTemplateController {

    @Autowired
    lateinit var jwtProvider: JwtProvider

    @Autowired
    lateinit var dishesTemplateRepository: DishesTemplateRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @JsonView(View.REST::class)
    @RequestMapping("dish_template/all",
            produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun getAllDishTemplateJSON(@RequestHeader("Authorization") token: String): List<DishTemplate> {
        return dishesTemplateRepository.findAllDishesUser(
                jwtProvider.getLoginFromToken(
                        token.substring(7))).toList()
    }

    @JsonView(View.REST::class)
    @RequestMapping("dish_template/save",
            method = [RequestMethod.POST],
            produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun addDishTemplate(@RequestBody dish: DishTemplate, @RequestHeader("Authorization") token: String) {
        println(dish)
        val user = userRepository.findByLogin(jwtProvider.getLoginFromToken(token.substring(7)))
        dish.user = user
        dishesTemplateRepository.save(dish)
        dish.dishTemplate_product.forEach { it.dishTemplate = dish }
        dishesTemplateRepository.save(dish)
    }

    @JsonView(View.REST::class)
    @RequestMapping("dish_template/search/{name}",
            produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun getSearchDishTemplate(@PathVariable name: String): List<DishTemplate> {
        return dishesTemplateRepository.findByName(name)
    }
}

