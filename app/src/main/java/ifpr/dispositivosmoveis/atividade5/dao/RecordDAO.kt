package ifpr.dispositivosmoveis.atividade5.dao

import ifpr.dispositivosmoveis.atividade5.models.Record
import ifpr.dispositivosmoveis.atividade5.models.User
import ifpr.dispositivosmoveis.atividade5.network.services.RecordService
import ifpr.dispositivosmoveis.atividade5.util.Environment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecordDAO {
    private val retrofit = Retrofit.Builder()
        .baseUrl(Environment.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service = retrofit.create(RecordService::class.java)


    fun insert(record: Record, finished: (record: Record) -> Unit) {
        service.insert(record).enqueue(object : Callback<Record> {
            override fun onResponse(call: Call<Record>, response: Response<Record>) {
                val user = response.body()!!
                finished(record)
            }
            override fun onFailure(call: Call<Record>, t: Throwable) { }
        })
    }

    fun getAll(userId: Long, finished: (records: List<Record>) -> Unit) {
        service.getAll(userId).enqueue(object : Callback<List<Record>> {
            override fun onResponse(call: Call<List<Record>>, response: Response<List<Record>>) {
                val records = response.body()!!
                response.body()
                finished(records)
            }
            override fun onFailure(call: Call<List<Record>>, t: Throwable) { }
        })
    }

    fun filter(userId: Long, send: Boolean, finished: (records: List<Record>) -> Unit) {
        service.filter(userId, send).enqueue(object : Callback<List<Record>> {
            override fun onResponse(call: Call<List<Record>>, response: Response<List<Record>>) {
                val records = response.body()!!
                response.body()
                finished(records)
            }
            override fun onFailure(call: Call<List<Record>>, t: Throwable) { }
        })
    }

    fun search(userId: Long, query: String, finished: (records: List<Record>) -> Unit) {
        service.search(userId, query).enqueue(object : Callback<List<Record>> {
            override fun onResponse(call: Call<List<Record>>, response: Response<List<Record>>) {
                val records = response.body()!!
                finished(records)
            }
            override fun onFailure(call: Call<List<Record>>, t: Throwable) { }
        })
    }

    fun update(record: Record, finished: (record: Record) -> Unit) {
        service.update(record.id!!, record).enqueue(object : Callback<Record> {
            override fun onResponse(call: Call<Record>, response: Response<Record>) {
                val record = response.body()!!
                finished(record)
            }
            override fun onFailure(call: Call<Record>, t: Throwable) { }
        })
    }

    fun save(record: Record, finished: (record: Record) -> Unit) {
        if (record.id == null)
            insert(record, finished)
        else
            update(record, finished)
    }
}