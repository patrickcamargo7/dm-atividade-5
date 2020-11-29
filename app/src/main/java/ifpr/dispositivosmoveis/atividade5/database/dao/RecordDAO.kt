package ifpr.dispositivosmoveis.atividade5.database.dao

import androidx.room.*
import ifpr.dispositivosmoveis.atividade5.models.Record

@Dao
interface RecordDAO {
    @Query("SELECT * FROM records WHERE user_id = :userId")
    fun getAll(userId: Long): List<Record>

    @Query("SELECT * FROM records WHERE user_id = :userId AND value >= 0")
    fun getAllPayments(userId: Long): List<Record>

    @Query("SELECT * FROM records WHERE user_id = :userId AND value < 0")
    fun getAllReceives(userId: Long): List<Record>

    @Query("SELECT * FROM records WHERE id IN (:ids)")
    fun getAllByIds(ids: IntArray): List<Record>

    @Insert
    fun insert(record: Record): Long

    @Insert
    fun insertAll(vararg record: Record): LongArray

    @Delete
    fun delete(record: Record)

    @Update
    fun update(record: Record)
}