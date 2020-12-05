package ifpr.dispositivosmoveis.atividade5.models

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*



data class Record(
    var value: Float,
    var person: String,
    var remarks: String,
    var send: Boolean
) {
    var id: Long? = null

    @SerializedName("user_id")
    var userId: Long? = null

    @SerializedName("create_date")
    var createDate: String = now()

    override fun equals(other: Any?) = other is Record && this.id == other.id

    fun now() : String {
        return formatDate(Date())
    }

    fun formatDate(date: Date) : String {
        var pattern: String = "dd/MM/yyyy";
        var simpleDateFormat: SimpleDateFormat = SimpleDateFormat(pattern);
        var result: String = simpleDateFormat.format(date)

        return result
    }

}