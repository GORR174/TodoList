package ru.catstack.todo.model

data class Command(val name: String, val helpText: String, val function: () -> Unit)