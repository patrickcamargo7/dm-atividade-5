package ifpr.dispositivosmoveis.atividade5.dao

import ifpr.dispositivosmoveis.atividade5.models.User
import ifpr.dispositivosmoveis.atividade5.network.services.UserService
import ifpr.dispositivosmoveis.atividade5.util.Environment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserDAO {
    private val retrofit = Retrofit.Builder()
        .baseUrl(Environment.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service = retrofit.create(UserService::class.java)

    fun authenticate(
        username: String,
        password: String,
        finished: (people: List<User>) -> Unit,
        onError: (t: Throwable) -> Unit
    ) {
        service.authenticate(username, password).enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                val users = response.body()!!

                if (users.isNotEmpty()) {
                    finished(users)
                    return
                }

                onError(Exception())
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                onError(t)
            }
        })
    }

    fun insert(user: User, finished: (person: User) -> Unit, onError: (t: Throwable) -> Unit) {
        service.insert(user).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.code() == 201) {
                    val user = response.body()!!
                    response.body()
                    finished(user)
                    return
                }
                throw Exception()
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                onError(t)
            }
        })
    }
}