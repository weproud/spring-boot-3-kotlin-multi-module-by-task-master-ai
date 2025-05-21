package com.weproud.app.admin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

/**
 * 관리자 API 애플리케이션 진입점
 */
@SpringBootApplication
@ComponentScan(basePackages = ["com.weproud"])
class AdminApplication

fun main(args: Array<String>) {
    runApplication<AdminApplication>(*args)
}
