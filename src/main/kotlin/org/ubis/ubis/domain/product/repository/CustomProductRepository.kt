package org.ubis.ubis.domain.product.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.ubis.ubis.domain.product.model.Product

interface CustomProductRepository {
    fun findAllPageableOrder(pageable: Pageable): Page<Product>
}