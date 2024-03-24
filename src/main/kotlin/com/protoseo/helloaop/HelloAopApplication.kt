package com.protoseo.helloaop

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HelloAopApplication

fun main(args: Array<String>) {
    runApplication<HelloAopApplication>(*args)
}
