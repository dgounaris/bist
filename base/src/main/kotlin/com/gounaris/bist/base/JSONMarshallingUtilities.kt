package com.gounaris.bist.base

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule

val objectMapper = ObjectMapper().also {
    it.registerModule(KotlinModule())
}