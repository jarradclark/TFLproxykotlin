package dev.jarradclark.tflproxykotlin.services.tfl

import dev.jarradclark.tflproxykotlin.services.models.Arrival

interface TFLClient {
    fun getArrivalForStop(stopName: String): List<Arrival>?
}