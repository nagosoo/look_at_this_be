package com.eunji.look_at_this

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class LookAtThisApplication

fun main(args: Array<String>) {
    runApplication<LookAtThisApplication>(*args)
}
