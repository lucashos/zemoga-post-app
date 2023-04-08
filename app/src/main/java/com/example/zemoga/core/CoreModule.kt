package com.example.zemoga.core

import org.koin.dsl.module

val coreModule = module {
    factory { Network().retrofit }
}