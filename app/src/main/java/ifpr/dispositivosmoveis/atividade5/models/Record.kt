package ifpr.dispositivosmoveis.atividade5.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "records",     foreignKeys = [
    ForeignKey(entity = User::class,
        parentColumns = ["id"],
        childColumns = ["user_id"]
    )]
)
data class Record(
    @ColumnInfo(name = "value") var value: Float,
    @ColumnInfo(name = "person") var person: String,
    @ColumnInfo(name = "remarks") var remarks: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null

    @ColumnInfo(name = "user_id")
    var userId: Long? = null

    @ColumnInfo(name = "registered_at")
    var createDate: java.sql.Date = java.sql.Date(System.currentTimeMillis())

    override fun equals(other: Any?) = other is Record && this.id == other.id
}