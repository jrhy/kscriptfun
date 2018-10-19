#!/usr/bin/env kscript

//DEPS org.jetbrains.kotlinx:kotlinx-coroutines-core:0.30.2
//INCLUDE command.kt

import kotlinx.coroutines.experimental.*
import java.lang.System.err

runBlocking {
    GlobalScope.launch {
        for (x in 1..3) {
            launch {
                val command = "sleep $x ; echo $x"
                println("( $command ) &")
                shell(command).showResult()
            }
        }
    }.join()
    println("they all worked!!!!")
}
