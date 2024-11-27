package dev.jarradclark.tflproxykotlin.services

import dev.jarradclark.tflproxykotlin.services.tfl.TFLClient
import dev.jarradclark.tflproxykotlin.services.models.ArrivalData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException

@Service
class DefaultTFLService @Autowired constructor(val tflClient: TFLClient): TFLService {

    private var currentStop = "490009538N"

    override fun getArrivalsForCurrentStop(): ArrivalData = getArrivalsForStop(currentStop)

    override fun getArrivalsForStop(stopName: String): ArrivalData {
        try {
            val arrivals = tflClient.getArrivalForStop(stopName)
            return ArrivalData(stopName, "TBC", arrivals?.sortedBy { it.timeToStation })
        } catch (e: RestClientException) {
            return ArrivalData(stopName, "TBC", emptyList())
        }
    }

    override fun setCurrentStop(stopName: String) {
        this.currentStop = stopName
    }

}