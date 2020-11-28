package ifpr.dispositivosmoveis.atividade5.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @ColumnInfo(name = "username") var username: String,
    @ColumnInfo(name = "password") var password: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null

    override fun equals(other: Any?) = other is User && this.id == other.id
}