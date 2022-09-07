        package com.iherrera.fundamentoscorrutinas

        import kotlinx.coroutines.*
        import kotlinx.coroutines.flow.Flow
        import kotlinx.coroutines.flow.flow
        import kotlin.random.Random

        fun main() {
        //    dispatchers()
//            nested()
//            changeWithContext()
//            sequences()
            basicFlows()
        }

        fun basicFlows() {
            newTopic("Flows basicos")
            runBlocking {
                launch {
                    getDataByFlow().collect { println(it) }
                }

                launch {
                    (1..50).forEach { _ ->
                        delay(someTime()/10)
                        println("Tarea 2")
                    }
                }
            }
        }
        fun getDataByFlow(): Flow<Float> {
            return flow { // resolver casos de codigo asincrono que devuelve multiples valores
                (1..5).forEach {
                    println("$it procesando datos...")
                    Thread.sleep(someTime())
                    emit(20 + it + Random.nextFloat())
                }
            }
        }

        fun sequences() {
            newTopic("Sequences")
            getDataBySeq().forEach { println("${it}%") }
        }

        fun getDataBySeq(): Sequence<Float> {
            return sequence {
                (1..5).forEach {
                    println("procesando datos...")
                    Thread.sleep(someTime())
                    yield(20 + it + Random.nextFloat())
                }
            }
        }

        fun changeWithContext() {
            runBlocking {
                newTopic("WithContext")
                startMsg()

                withContext(newSingleThreadContext("Cursos Android ANT")) {
                    startMsg()
                    delay(someTime())
                    println("CursosAndroidANT")
                    endMsg()
                }

                withContext(Dispatchers.IO) {
                    startMsg()
                    delay(someTime())
                    println("Peticion al servidor")
                    endMsg()
                }

                endMsg()
            }
        }

        fun nested() {
            runBlocking {
                newTopic("Anidar")

                val job = launch {
                    startMsg()

                    launch {
                        startMsg()
                        delay(someTime())
                        println("Otra tarea")
                        endMsg()
                    }

                    val subJob = launch(Dispatchers.IO) {
                        startMsg()

                        launch(newSingleThreadContext("Cursos Android ANT")) {
                            startMsg()
                            println("tarea cursos android ant")
                            endMsg()
                        }

                        delay(someTime())
                        println("tarea en el servidor")
                        endMsg()
                    }
                    delay(someTime()/4)
                    subJob.cancel()
                    println("SubJob cancelado...")

                    var sum = 0
                    (1..100).forEach {
                        sum += it
                        delay(someTime()/100)
                    }
                    println("Sum = $sum")
                    endMsg()
                }

                delay(someTime()/2)
                job.cancel()
                println("Job cancelado...")
            }
        }

        fun dispatchers() {
            runBlocking {
                newTopic("Dispatchers")
                launch {
                    startMsg()
                    println("None")
                    endMsg()
                }

                launch(Dispatchers.IO) { // tareas de larga duracion
                    startMsg()
                    println("IO")
                    endMsg()
                }

                launch(Dispatchers.Unconfined) { // procesos donde no necesitas compartir datos con otras corrutinas
                    startMsg()
                    println("Unconfined")
                    endMsg()
                }

                launch(Dispatchers.Default) { // uso intensivo de la CPU
                    startMsg()
                    println("Default")
                    endMsg()
                }

                launch(Dispatchers.Main) { // solo para Android
                    startMsg()
                    println("Main")
                    endMsg()
                }
            }
        }
