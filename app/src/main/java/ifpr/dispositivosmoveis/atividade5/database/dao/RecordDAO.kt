package ifpr.dispositivosmoveis.atividade5.database.dao

import androidx.room.*
import ifpr.dispositivosmoveis.atividade5.models.Record

@Dao
interface RecordDAO {
    @Query("SELECT * FROM records WHERE user_id = :userId")
    fun getAll(userId: Long): List<Record>

    @Query("SELECT sum(value) FROM records WHERE user_id = :userId AND value < 0")
    fun getTotalPayments(userId: Long): Float

    @Query("SELECT sum(value) FROM records WHERE user_id = :userId AND value >= 0")
    fun getTotalReceives(userId: Long): Float

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