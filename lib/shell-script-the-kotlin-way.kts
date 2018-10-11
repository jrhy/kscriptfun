#!/usr/bin/env kscript

//DEPS org.jetbrains.kotlinx:kotlinx-coroutines-core:0.30.2
//INCLUDE command.kt

import kotlinx.coroutines.experimental.*
import java.lang.System.err

fun CoroutineScope.asyncMain() {
    for (x in 1..3) {
        launch {
            val command = "sleep $x ; echo $x"
            println("( $command ) &")
            shell(command).apply {
                if (stdout.isNotEmpty())
                    println(stdout.trim())
                if (stderr.isNotEmpty())
                    err.println(stderr.trim())
                if (exitCode != 0) {
                    println("failed with exit code $exitCode")
                    System.exit(1)
                }
            }
        }
    }
}

runBlocking {
    GlobalScope.launch {
        asyncMain()
    }.join()
    println("they all worked!!!!")
}

