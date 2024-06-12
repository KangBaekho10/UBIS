package org.ubis.ubis.domain.product.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.ubis.ubis.domain.product.model.Product

interface ProductRepositoryCustom {
   fun findProductList(pageable: Pageable, name: String?): Page<Product>


   //잘하자 좀
}