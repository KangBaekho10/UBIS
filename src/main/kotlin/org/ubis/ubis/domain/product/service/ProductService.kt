package org.ubis.ubis.domain.product.service

import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.ubis.ubis.domain.product.dto.CreateProductRequest
import org.ubis.ubis.domain.product.dto.ProductResponse
import org.ubis.ubis.domain.product.dto.UpdateProductRequest
import org.ubis.ubis.domain.product.model.Product
import org.ubis.ubis.domain.product.model.toProductResponse
import org.ubis.ubis.domain.product.repository.ProductRepository

@Service
class ProductService(
    private val productRepository: ProductRepository
) {

    fun getProductEntity(productId:Long):Product{
        return productRepository.findByIdOrNull(productId)
            ?: throw RuntimeException("Product with ID $productId not found")
    }

    fun getProductList(): List<ProductResponse> {
        return productRepository.findAll().map { it.toProductResponse() }
    }

    fun getProduct(productId: Long): ProductResponse {
        val result = productRepository.findByIdOrNull(productId)
            ?: throw RuntimeException("Product with ID $productId not found")
        return result.toProductResponse()
    }

    @Transactional
    fun createProduct(request: CreateProductRequest): ProductResponse {
        return productRepository.save(
            Product(
                name = request.name,
                description = request.description,
                price = request.price,
                imgs = request.imgs
            )
        ).toProductResponse()
    }

    @Transactional
    fun updateProduct(productId: Long, request: UpdateProductRequest): ProductResponse {
        val result = productRepository.findByIdOrNull(productId)
            ?: throw RuntimeException("Product with ID $productId not found")

        result.name = request.name
        result.description = request.description
        result.price = request.price
        result.imgs = request.imgs
        return productRepository.save(result).toProductResponse()
    }

    @Transactional
    fun deleteProduct(productId: Long) {
        val result = productRepository.findByIdOrNull(productId)
            ?: throw RuntimeException("Product with ID $productId not found")
        return productRepository.delete(result)
    }
}