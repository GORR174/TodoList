package ru.catstack.todo.di

import org.kodein.di.DKodein
import org.kodein.di.Kodein
import org.kodein.di.direct

val kodein = Kodein {
    import(appModule)
}

val kodeinDirect: DKodein
    get() = kodein.direct