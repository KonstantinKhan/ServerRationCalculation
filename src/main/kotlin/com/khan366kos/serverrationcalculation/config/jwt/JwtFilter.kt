package com.khan366kos.serverrationcalculation.config.jwt

import com.khan366kos.serverrationcalculation.config.CustomUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtFilter : GenericFilterBean() {

    val AUTHORIZATION = "Authorization"

    @Autowired
    lateinit var jwtProvider: JwtProvider

    @Autowired
    lateinit var customUserDetailsService: CustomUserDetailsService

    override fun doFilter(servletRequest: ServletRequest?, servletResponse: ServletResponse?, filterChain: FilterChain?) {

        val response = servletResponse as HttpServletResponse
        response.setHeader("Access-Control-Allow-Origin", "*")
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PATCH, PUT")
        response.setHeader("Access-Control-Max-Age", "3600")
        response.setHeader("Access-Control-Allow-Credentials", "true")
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization")

        val token = getTokenFromRequest(servletRequest as HttpServletRequest)
        if (token != null && jwtProvider.validateToken(token)) {
            jwtProvider.login = jwtProvider.getLoginFromToken(token)
            val customUserDetails = customUserDetailsService.loadUserByUsername(jwtProvider.login)
            val auth = UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.authorities)
            SecurityContextHolder.getContext().authentication = auth
        }
        if (servletRequest.method == "OPTIONS") {
            response.status = HttpServletResponse.SC_OK
        }
        filterChain?.doFilter(servletRequest, servletResponse)
    }

    fun getTokenFromRequest(request: HttpServletRequest): String? {
        val bearer = request.getHeader(AUTHORIZATION)
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7)
        }
        return null
    }
}