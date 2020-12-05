package ifpr.dispositivosmoveis.atividade5.network.services

import ifpr.dispositivosmoveis.atividade5.models.Record
import retrofit2.Call
import retrofit2.http.*

interface RecordService {
    @POST("records")
    @Headers("Content-Type: application/json")
    fun insert(@Body person: Record): Call<Record>

    @PATCH("records/{id}")
    @Headers("Content-Type: application/json")
    fun update(@Path("id") id: Long, @Body record: Record): Call<Record>

    @GET("records")
    fun getAll(@Query("user_id") userId: Long): Call<List<Record>>

    @GET("records")
    fun filter(@Query("user_id") userId: Long, @Query("send") send: Boolean): Call<List<Record>>

    @GET("records")
    fun search(@Query("user_id") userId: Long, @Query("person_like") query: String): Call<List<Record>>
}