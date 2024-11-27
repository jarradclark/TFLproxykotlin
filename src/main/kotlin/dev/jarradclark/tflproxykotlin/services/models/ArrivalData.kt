package dev.jarradclark.tflproxykotlin.services.models

data class ArrivalData(
    val currentStop: String,
    val stopName: String,
    val arrivals: List<Arrival>?
)