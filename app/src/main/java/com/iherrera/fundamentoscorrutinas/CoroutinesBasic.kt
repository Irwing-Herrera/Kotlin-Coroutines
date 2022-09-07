package com.iherrera.fundamentoscorrutinas

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce

fun main() {
    //globalScope()
    //suspendFun()
    newTopic("Constructores de corrutinas")
//    cRunBlocking()
//    cLaunch()
//    cAsync()
//    cJob()
//    deferred()
    cProduce()

    readLine()
}

fun cProduce() = runBlocking {
    newTopic("Produce")
    val names = produceNames()
    names.consumeEach { println(it) }
}
fun CoroutineScope.produceNames(): ReceiveChannel<String> = produce {
    (1..5).forEach {
        send("name$it")
    }
}


fun deferred() {
    runBlocking {
        newTopic("Deferred")
        val deferred = async {
            startMsg()
            delay(someTime())
            println("deferred....")
            endMsg()
        }

        println("Deferred: $deferred")
        println("Valor de Deferred.await: ${deferred.await()}")
    }
}

fun cJob() {
    runBlocking {
        newTopic("Job")
        val job = launch {
            startMsg()
            delay(2_100)
            println("job....")
            endMsg()
        }
        println("Job: $job")

        println("isActive: ${job.isActive}")
        println("isCancelled: ${job.isCancelled}")
        println("isCompleted: ${job.isCompleted}")

        delay(someTime())
        println("Tarea cancelada o interrumpida")
        job.cancel()

        println("isActive: ${job.isActive}")
        println("isCancelled: ${job.isCancelled}")
        println("isCompleted: ${job.isCompleted}")
    }
}

fun cAsync() {
    runBlocking {
        newTopic("Async")
        var result = async {
            startMsg()
            delay(someTime())
            println("async....")
            endMsg()
        }

        println("Result: ${result.await()}")
    }
}


// No es necesario retornar algun valor, se ocuparia para rezalizar la recopilacion de datos para el programador
fun cLaunch() {
    runBlocking {
        newTopic("Launch")
        launch {
            startMsg()
            delay(someTime())
            println("runBlocking....")
            endMsg()
        }
    }
}

// Suspende un hilo hasta que nuestro bloque de codigo termine
fun cRunBlocking() {
    newTopic("RunBlocking")
    runBlocking {
        startMsg()
        delay(someTime())
        println("runBlocking....")
        endMsg()
    }
}

fun suspendFun() {
    newTopic("Suspend")
    Thread.sleep(someTime())
//    delay(someTime())
    GlobalScope.launch {
        delay(someTime())
    }
}

fun globalScope() {
    newTopic("Global Scope")

    GlobalScope.launch {
        startMsg()
        delay(someTime())
        println("Mi corrutina")
        endMsg()
    }
}

fun startMsg() {
    println("Comenzando corrutina -${Thread.currentThread().name}-")
}

fun endMsg() {
    println("Corrutina -${Thread.currentThread().name}- finalizada \n")
}
