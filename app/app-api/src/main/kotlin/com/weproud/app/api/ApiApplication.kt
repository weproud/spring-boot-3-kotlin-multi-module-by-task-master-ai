package com.weproud.app.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

/**
 * API 애플리케이션 진입점
 */
@SpringBootApplication
@ComponentScan(basePackages = ["com.weproud"])
class ApiApplication

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}
