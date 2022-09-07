package com.iherrera.fundamentoscorrutinas

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread
import kotlin.random.Random

private const val SEPARATOR = "=================="

fun main() {
//    lambda()
//    threads()
    coroutinesVsThreads()
}

fun newTopic(topic: String) {
    println("\n$SEPARATOR $topic $SEPARATOR\n")
}

fun coroutinesVsThreads() {
    newTopic("Corrutinas vs Threads")

    runBlocking {
        (1..1_000_000).forEach { _ ->
            launch {
                delay(someTime())
                print("*")
            }
        }
    }
//    (1..1_000_000).forEach { _ ->
//        thread {
//            Thread.sleep(someTime())
//            println("*")
//        }
//    }
}

fun threads() {
    newTopic("Threads")

    println("threads ${multiThread(3, 4)}")
    multiThreadLambda(3, 4) { result ->
        println("multiThreadLambda $result")
    }
}

fun multiThread(x: Int, y: Int): Int {
    var result = 0

    thread {
        Thread.sleep(someTime())
        result = x * y
    }

    return result
}

fun multiThreadLambda(x: Int, y: Int, callback: (result: Int) -> Unit) {
    var result = 0

    thread {
        Thread.sleep(someTime())
        result = x * y

        callback(result)
    }
}

fun someTime(): Long = Random.nextLong(500, 2_000)

fun lambda() {
    newTopic("Lambda")

    println("lambda ${multi(2, 3)}")

    multiLambda(2, 3) { result ->
        println("multiLambda $result")
    }
}

fun multiLambda(x: Int, y: Int, callback: (result: Int) -> Unit) {
    callback(x * y)
}

fun multi(x: Int, y: Int): Int {
    return x * y
}

