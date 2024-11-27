package dev.jarradclark.tflproxykotlin.services.tfl

import dev.jarradclark.tflproxykotlin.services.models.Arrival
import dev.jarradclark.tflproxykotlin.services.tfl.models.TFLArrivalRecord
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.converter.gson.GsonConverterFactory

@Service
class DefaultTFLClient : TFLClient {

    private val retrofit = TFLRetrofitInstance.getInstance()
    private val tflAPI = retrofit.create(TFLApiInterface::class.java)

    override fun getArrivalForStop(stopName: String): List<Arrival>? {
        val response = tflAPI.getTFLArrivalForStop(stopName).execute()

        if (response.isSuccessful) {
            val tflBusList: List<TFLArrivalRecord>? = response.body()
            return tflBusList?.map { Arrival(it.lineName, it.destinationName, it.timeToStation, "") }
        } else {
            throw RestClientException("Did not successfully get a response from TFL")
        }
    }
}

interface TFLApiInterface {
    @GET("StopPoint/{stopId}/arrivals")
    fun getTFLArrivalForStop(@Path("stopId") stopId: String): Call<List<TFLArrivalRecord>>
}

object TFLRetrofitInstance {
    private const val BASE_URL = "https://api.tfl.gov.uk/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}