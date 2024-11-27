package dev.jarradclark.tflproxykotlin.services.tfl.models

data class TFLArrivalRecord(
    val lineName: String,
    val destinationName: String,
    val timeToStation: Int
)