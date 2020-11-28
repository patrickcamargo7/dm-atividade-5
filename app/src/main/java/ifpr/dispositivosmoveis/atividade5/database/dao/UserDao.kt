package ifpr.dispositivosmoveis.atividade5.database.dao

import androidx.room.*
import ifpr.dispositivosmoveis.atividade5.models.User

@Dao
interface UserDAO {
    @Query("SELECT * FROM users")
    fun getAll(): List<User>

    @Query("SELECT * FROM users WHERE id IN (:ids)")
    fun getAllByIds(ids: IntArray): List<User>

    @Query("SELECT * FROM users WHERE username = (:username) AND password = (:password)")
    fun where(username: String, password: String): List<User>

    @Insert
    fun insert(person: User): Long

    @Insert
    fun insertAll(vararg user: User): LongArray

    @Delete
    fun delete(person: User)

    @Update
    fun update(person: User)
}