package org.ubis.ubis.domain.order.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.ubis.ubis.domain.order.dto.OrderResponse
import org.ubis.ubis.domain.order.service.OrderService

@RestController
@RequestMapping("/products/{productId}/orders")
class OrderController(
    private val orderService: OrderService,
) {
    @GetMapping
    fun getOrderList(): ResponseEntity<List<OrderResponse>> {
        return ResponseEntity.ok(orderService.getOrderList())
    }

    @GetMapping("/{orderId}")
    fun getOrder(
        @PathVariable productId: Long,
        @PathVariable orderId: Long
    ): ResponseEntity<OrderResponse> {
        return ResponseEntity.ok(orderService.getOrder(productId, orderId))
    }

    @PostMapping
    fun createOrder(
        @PathVariable productId: Long,
    ): ResponseEntity<OrderResponse> {
        val order: OrderResponse = orderService.createOrder(productId)
        return ResponseEntity.ok(order)
    }

    @DeleteMapping("{orderId}")
    fun deleteOrder(
        @PathVariable productId: Long,
        @PathVariable orderId: Long
    ): ResponseEntity<Unit> {
        return ResponseEntity.ok(orderService.deleteOrder(productId,orderId))
    }
}
