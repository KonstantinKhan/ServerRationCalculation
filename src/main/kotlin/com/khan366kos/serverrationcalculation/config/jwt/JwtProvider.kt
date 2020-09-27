package com.khan366kos.serverrationcalculation.config.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.lang.Exception
import java.time.Instant
import java.util.*

@Component
class JwtProvider {

    lateinit var login: String

    fun generateToken(login: String, expires: Int): String {
        val now: Long = Instant.now().toEpochMilli()
        return Jwts.builder()
                .setSubject(login)
                .setExpiration(Date(now + expires * 1000))
                .signWith(SignatureAlgorithm.HS512, "admin")
                .compact()
    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parser().setSigningKey("admin").parseClaimsJws(token)
            return true
        } catch (e: Exception) {
            println("exception: ${e}")
        }
        return false
    }

    fun getLoginFromToken(token: String): String {
        val claims = Jwts.parser().setSigningKey("admin").parseClaimsJws(token).body
        return claims.subject
    }
}