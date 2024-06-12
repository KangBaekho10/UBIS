package org.ubis.ubis.domain.product.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.ubis.ubis.domain.product.model.Product

interface ProductRepository
    : JpaRepository<Product, Long>,
    CustomProductRepository {

    }