package com.khan366kos.serverrationcalculation.controllers

import com.fasterxml.jackson.annotation.JsonView
import com.khan366kos.serverrationcalculation.config.jwt.JwtProvider
import com.khan366kos.serverrationcalculation.models.Product
import com.khan366kos.serverrationcalculation.models.User
import com.khan366kos.serverrationcalculation.models.View
import com.khan366kos.serverrationcalculation.repo.ProductsRepository
import com.khan366kos.serverrationcalculation.repo.RationsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.lang.reflect.Method

@Controller
class ProductsController {

    @Autowired
    lateinit var productsRepository: ProductsRepository

    @Autowired
    lateinit var rationsRepository: RationsRepository

    @Autowired
    lateinit var jwtProvider: JwtProvider

    /* @RequestMapping("/add_product", produces = [MediaType.APPLICATION_JSON_VALUE])
     @ResponseBody
     @JsonView(View.REST::class)
     fun addProduct(): Product {
         val product = Product(0, "first_product", 100.0,
                 100.0, 100.0, 100.0)

 //        val ration = rationsRepository.findById(10).get()

         val ration = Ration(0, Date(), 100.0, 100.0, 100.0, 100.0)

         ration.ration_product.add(RationProduct(0, product, ration, "b"))

         val ration_product = RationProduct(0, product, ration, "b")

         product.ration_product.add(ration_product)

         productsRepository.save(product)

         return productsRepository.findAll().first()
     }*/

    /* @RequestMapping("/remove", produces = [MediaType.APPLICATION_JSON_VALUE])
     @ResponseBody
     @JsonView(View.REST::class)
     fun removeProduct() {
         val product = productsRepository.findByName("first_product").first()
         productsRepository.delete(product)
     }*/

    //     @JsonView(View.REST::class)
//     @RequestMapping("/product/{id}", produces = ["application/json"], method = [RequestMethod.GET])
//     @ResponseBody
//     fun getProductJSON(@PathVariable id: Long): Product {
//         return repository.findById(id).get()
//     }

    // Продукты, удовлетворяющие поисковому запросу
    @RequestMapping("/products/search/{name}",
            produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    @JsonView(View.REST::class)
    fun getSearchProducts(@PathVariable name: String): List<Product> {
        return productsRepository.findByName(name, jwtProvider.login).toList()
    }

    // Отдаем все продукты, имеющиеся в базе.
    @RequestMapping("/products/all",
            produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    @JsonView(View.REST::class)
    fun getAllProductsJSON(): List<Product> {
        return productsRepository.findAllProductsUser(jwtProvider.login).toList()
    }

//     @RequestMapping("products/all")
//     fun getAllProducts(model: Model): String {
//         model.addAttribute("products", repository.findAll())
//         return "allproducts"
//     }
//
//     @RequestMapping("/product", method = [RequestMethod.POST], produces = [MediaType.APPLICATION_JSON_VALUE])
//     @ResponseBody
//     fun addProduct(@RequestBody product: Product) {
//         repository.save(product)
//     }
//
//     @RequestMapping("/product/{id}", method = [RequestMethod.DELETE], produces = [MediaType.APPLICATION_JSON_VALUE])
//     @ResponseBody
//     fun deleteProduct(@PathVariable id: Long) {
//         repository.deleteById(id)
//     }
//
//     @RequestMapping("/product", method = [RequestMethod.PUT], produces = [MediaType.APPLICATION_JSON_VALUE])
//     @ResponseBody
//     fun editProduct(@RequestBody product: Product) {
//         val resProduct = repository.findById(product.productId).get()
//         resProduct.name = product.name
//         resProduct.calories = product.calories
//         resProduct.proteins = product.proteins
//         resProduct.fats = product.fats
//         resProduct.carbohydrates = product.carbohydrates
//
//         repository.save(resProduct)
//     }
//
//     @RequestMapping("/product", method = [RequestMethod.PATCH], produces = [MediaType.APPLICATION_JSON_VALUE])
//     @ResponseBody
//     fun updateProduct(@RequestBody product: Product) {
//         val resProduct = repository.findById(product.productId).get()
//         if (resProduct.name != product.name)
//             resProduct.name = product.name
//         if (resProduct.calories != product.calories)
//             resProduct.calories = product.calories
//         if (resProduct.proteins != product.proteins)
//             resProduct.proteins = product.proteins
//         if (resProduct.fats != product.fats)
//             resProduct.fats = product.fats
//         if (resProduct.carbohydrates != product.carbohydrates)
//             resProduct.carbohydrates = product.carbohydrates
//         repository.save(resProduct)
//     }
}