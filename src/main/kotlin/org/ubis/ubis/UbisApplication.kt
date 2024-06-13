package org.ubis.ubis

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UbisApplication

fun main(args: Array<String>) {
    runApplication<UbisApplication>(*args)
}
