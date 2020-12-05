package ifpr.dispositivosmoveis.atividade5.network.services

import ifpr.dispositivosmoveis.atividade5.models.User
import retrofit2.Call
import retrofit2.http.*

interface UserService {
    @POST("users")
    @Headers("Content-Type: application/json")
    fun insert(@Body person: User): Call<User>

    @GET("users")
    @Headers("Content-Type: application/json")
    fun authenticate(@Query("username") username: String, @Query("password") password: String): Call<List<User>>
}