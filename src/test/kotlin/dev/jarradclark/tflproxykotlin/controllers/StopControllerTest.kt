package dev.jarradclark.tflproxykotlin.controllers

import com.ninjasquad.springmockk.MockkBean
import dev.jarradclark.tflproxykotlin.services.TFLService
import dev.jarradclark.tflproxykotlin.services.models.Arrival
import dev.jarradclark.tflproxykotlin.services.models.ArrivalData
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.hamcrest.collection.IsCollectionWithSize
import org.junit.jupiter.api.BeforeEach
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import kotlin.test.Test


@SpringBootTest
class StopControllerTest {

    @MockkBean
    lateinit var mockTFLService: TFLService

    lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(StopController(mockTFLService)).build()
    }

    @Test
    fun `should call service for Arrival data for default stop`() {
        val arrivalData = ArrivalData(
            "TestStop", "Test Stop",
            listOf(Arrival("Test Name", "Test Destination", 1, "Due"))
        )
        every { mockTFLService.getArrivalsForCurrentStop() } returns arrivalData
        mockMvc.perform(get("/allArrivals"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.currentStop").value(arrivalData.currentStop))
            .andExpect(jsonPath("$.stopName").value(arrivalData.stopName))
            .andExpect(jsonPath("$.arrivals", IsCollectionWithSize.hasSize<Any>(1)))
            .andExpect(jsonPath("$.arrivals.[0].destinationName").value("Test Destination"))
    }

    @Test
    fun `should retrieve arrival data for a specific stop`() {
        val arrivalData = ArrivalData(
            "Specific Stop", "Specific Stop Name", emptyList()
        )
        every { mockTFLService.getArrivalsForStop("SpecificStopID") } returns arrivalData

        mockMvc.perform(get("/arrivals/SpecificStopID"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.currentStop").value(arrivalData.currentStop))
            .andExpect(jsonPath("$.stopName").value(arrivalData.stopName))
    }

    @Test
    fun `should accept changes of stop and return new data`() {
        val arrivalData = ArrivalData(
            "New Stop", "New Stop Name", emptyList()
        )
        every { mockTFLService.getArrivalsForCurrentStop() } returns arrivalData
        every { mockTFLService.setCurrentStop(any()) } just runs

        mockMvc.perform(post("/changeCurrentStop/NewStopID"))
            .andExpect(status().isAccepted);

        mockMvc.perform(get("/allArrivals"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.currentStop").value(arrivalData.currentStop))
            .andExpect(jsonPath("$.stopName").value(arrivalData.stopName))
    }

}