package dev.jarradclark.tflproxykotlin.controllers

import dev.jarradclark.tflproxykotlin.services.TFLService
import dev.jarradclark.tflproxykotlin.services.models.ArrivalData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class StopController(@Autowired private val tflService: TFLService) {

    @GetMapping("/allArrivals")
    fun listAllArrivals(): ResponseEntity<ArrivalData> {
        return ResponseEntity<ArrivalData>(tflService.getArrivalsForCurrentStop(),HttpStatus.OK)
    }

    @GetMapping("/arrivals/{stopId}")
    fun listAllArrivalsForStop(@PathVariable stopId: String): ResponseEntity<ArrivalData> {
        return ResponseEntity<ArrivalData>(tflService.getArrivalsForStop(stopId),HttpStatus.OK)
    }

    @PostMapping("/changeCurrentStop/{stopId}")
    fun changeCurrentStop(@PathVariable stopId: String): ResponseEntity<String> {
        tflService.setCurrentStop(stopId)
        return ResponseEntity<String>("Stop changed to $stopId", HttpStatus.ACCEPTED)
    }

}