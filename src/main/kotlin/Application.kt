package com.example

import com.example.com.inventorymanagement.config.configureDatabases
import com.example.com.inventorymanagement.config.configureHTTP
import com.example.com.inventorymanagement.config.configureMonitoring
import com.example.com.inventorymanagement.config.configureSecurity
import com.example.com.inventorymanagement.config.configureSerialization
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureDatabases()
    configureHTTP()
    configureSecurity()
    configureMonitoring()
    configureRouting()
}
