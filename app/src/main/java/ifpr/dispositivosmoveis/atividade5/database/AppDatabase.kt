package ifpr.dispositivosmoveis.atividade5.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ifpr.dispositivosmoveis.atividade5.database.dao.UserDAO
import ifpr.dispositivosmoveis.atividade5.models.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDAO(): UserDAO

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