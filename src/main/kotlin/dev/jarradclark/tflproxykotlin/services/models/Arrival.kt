package dev.jarradclark.tflproxykotlin.services.models

data class Arrival(
    val lineName: String,
    val destinationName: String,
    val timeToStation: Int,
    val arrivalMessage: String
)
