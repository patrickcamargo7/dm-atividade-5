package ifpr.dispositivosmoveis.atividade5.database

import android.content.Context
import androidx.room.*
import ifpr.dispositivosmoveis.atividade5.database.dao.RecordDAO
import ifpr.dispositivosmoveis.atividade5.database.dao.UserDAO
import ifpr.dispositivosmoveis.atividade5.models.Record
import ifpr.dispositivosmoveis.atividade5.models.User

@Database(entities = [User::class, Record::class], version = 3)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDAO(): UserDAO
    abstract fun recordDAO(): RecordDAO

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder<AppDatabase>(
                    context.getApplicationContext(),
                    AppDatabase::class.java,
                    "app-db"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE as AppDatabase
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}

public class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?):
            java.sql.Date {
        return java.sql.Date(value ?: 0)
    }

    @TypeConverter
    fun dateToTimestamp(date: java.sql.Date?)
            : Long {
        return date?.getTime() ?: 0
    }
}