package com.khan366kos.serverrationcalculation.controllers

import com.khan366kos.serverrationcalculation.config.jwt.JwtProvider
import com.khan366kos.serverrationcalculation.models.Token
import com.khan366kos.serverrationcalculation.models.User
import com.khan366kos.serverrationcalculation.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
class AuthController {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var jwtProvider: JwtProvider

    @RequestMapping("/register", method = [RequestMethod.POST], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun registerUser(@RequestBody user: User): String {
        val u = User(0, user.userName, user.userPassword, null)
        userService.saveUser(u)
        return "OK"
    }

    @RequestMapping("/auth", method = [RequestMethod.POST], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun auth(@RequestBody user: User): Token {
        val expires = 3600
        val u = userService.findByLoginAndPassword(user.userName, user.userPassword)
        val t = Token(jwtProvider.generateToken(u!!.userName, expires), expires.toString(), u.userId)
        return t
    }
}