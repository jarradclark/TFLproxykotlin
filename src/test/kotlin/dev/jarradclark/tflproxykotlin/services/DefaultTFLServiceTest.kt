package dev.jarradclark.tflproxykotlin.services

import dev.jarradclark.tflproxykotlin.services.tfl.TFLClient
import dev.jarradclark.tflproxykotlin.services.models.Arrival
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.web.client.RestClientException

class DefaultTFLServiceTest {

    private val first = Arrival("First", "First Destination", 50, "Due")
    private val middle = Arrival("Second", "Second Destination", 100, "1m")
    private val last = Arrival("Last", "Last Destination", 150, "2m")
    private val arrivalArray = listOf(middle, last, first)

    private val mockTFLClient: TFLClient = mockk<TFLClient>()
    private val tflService: TFLService = DefaultTFLService(mockTFLClient)

    @Test
    fun `should return all arrivals in correct order`() {
        every { mockTFLClient.getArrivalForStop("490009538N") } returns arrivalArray
        val arrivalData = tflService.getArrivalsForCurrentStop()

        assertEquals(first, arrivalData.arrivals?.first())
        assertEquals(middle, arrivalData.arrivals?.get(1))
        assertEquals(last, arrivalData.arrivals?.last())
    }

    @Test
    fun `should return data from selected Stop`() {
        val testResponse = Arrival("Test Line Name", "Test Destination", 123, "2m")
        every { mockTFLClient.getArrivalForStop("TestStop") } returns listOf(testResponse)
        val arrivalData = tflService.getArrivalsForStop("TestStop")
        assertEquals(testResponse, arrivalData.arrivals?.first())
    }

    @Test
    fun `should return new arrival data when stop changed`() {
        val newArrival = Arrival("New Line", "New Destination", 111, "1m")
        every { mockTFLClient.getArrivalForStop("NewStop") } returns listOf(newArrival)
        tflService.setCurrentStop("NewStop")
        val arrivalData = tflService.getArrivalsForCurrentStop()
        assertEquals(newArrival, arrivalData.arrivals?.first())
    }

    @Test
    fun `should suppress errors generated on the API`() {
        every { mockTFLClient.getArrivalForStop("THROWS") } throws RestClientException("Invlaid")
        val arrivalData = tflService.getArrivalsForStop("THROWS")
        assertEquals(0, arrivalData.arrivals?.size)
    }

}