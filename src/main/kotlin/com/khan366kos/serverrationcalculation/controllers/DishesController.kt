package com.khan366kos.serverrationcalculation.controllers

import com.fasterxml.jackson.annotation.JsonView
import com.khan366kos.serverrationcalculation.config.jwt.JwtProvider
import com.khan366kos.serverrationcalculation.models.Dish
import com.khan366kos.serverrationcalculation.models.Ration
import com.khan366kos.serverrationcalculation.models.RationDish
import com.khan366kos.serverrationcalculation.models.View
import com.khan366kos.serverrationcalculation.repo.DishesRepository
import com.khan366kos.serverrationcalculation.repo.RationsRepository
import com.khan366kos.serverrationcalculation.repo.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.text.SimpleDateFormat
import javax.servlet.http.HttpServletRequest

@Controller
class DishesController {

    @Autowired
    lateinit var dishesRepository: DishesRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var jwtProvider: JwtProvider

    @Autowired
    lateinit var rationsRepository: RationsRepository

    //    @JsonView(View.REST::class)
//    @RequestMapping("/dish", produces = ["application/json"])
//    @ResponseBody
//    fun getDishJSON(): Dish {
//        return repository.findAll().first()
//    }
//
    @JsonView(View.REST::class)
    @RequestMapping("/dishes/all",
            produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun getAllDishJSON(@RequestHeader("Authorization") token: String): List<Dish> {
        val dishes = dishesRepository.findAll()
        return dishesRepository.findAllDishesUser(jwtProvider.getLoginFromToken(token.substring(7))).toList()
    }

    @RequestMapping("/dish/save/{date}",
            method = [RequestMethod.PATCH],
            produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun saveDish(@PathVariable date: String,
                 @RequestBody rationDish: RationDish,
                 @RequestHeader("Authorization") token: String) {

        val user = userRepository.findByLogin(jwtProvider.getLoginFromToken(token.substring(7)))
        rationDish.dish.user = user
        dishesRepository.save(rationDish.dish)

        rationDish.dish.dish_product.forEach { it.dish = rationDish.dish }
        dishesRepository.save(rationDish.dish)

        val ration = rationsRepository.findByDate(SimpleDateFormat("yyyy-MM-dd").parse(date),
                jwtProvider.getLoginFromToken(token.substring(7)))

        ration.update()

        rationsRepository.save(ration)
        println("end saveDish()")
    }

    @RequestMapping("/dishes/search/{name}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    @JsonView(View.REST::class)
    fun getSearchDishes(@PathVariable name: String): List<Dish> {
        return dishesRepository.findByName(name)
    }
}