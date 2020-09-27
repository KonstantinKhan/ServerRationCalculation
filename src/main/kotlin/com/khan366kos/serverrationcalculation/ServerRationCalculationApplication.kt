package com.khan366kos.serverrationcalculation

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.io.IOException
import javax.servlet.*
import javax.servlet.http.HttpServletResponse

@SpringBootApplication
class ServerRationCalculationApplication

fun main(args: Array<String>) {
    runApplication<ServerRationCalculationApplication>(*args)
}

@Configuration
@EnableWebMvc
class WebConfig : WebMvcConfigurer {

    override fun configureContentNegotiation(
            configurer: ContentNegotiationConfigurer) {
        println("configureContentNegotiation")
        configurer
                .favorParameter(false)
                .ignoreAcceptHeader(true)
                .defaultContentType(MediaType.TEXT_HTML)
                .mediaType("json", MediaType.APPLICATION_JSON)
    }
}

/*
@Component
class SimpleCORSFilter : Filter {
    @Throws(IOException::class, ServletException::class)
    override fun doFilter(req: ServletRequest?, res: ServletResponse, chain: FilterChain) {
        val response = res as HttpServletResponse
        response.setHeader("Access-Control-Allow-Origin", "*")
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PATCH, PUT")
        response.setHeader("Access-Control-Max-Age", "3600")
        response.setHeader("Access-Control-Allow-Credentials", "true")
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization")
        chain.doFilter(req, res)
    }

    override fun init(filterConfig: FilterConfig?) {}
    override fun destroy() {}
}*/
