package dev.jarradclark.tflproxykotlin.services

import dev.jarradclark.tflproxykotlin.services.models.ArrivalData

interface TFLService {
    fun getArrivalsForCurrentStop(): ArrivalData
    fun getArrivalsForStop(stopName: String): ArrivalData
    fun setCurrentStop(stopName: String)
}