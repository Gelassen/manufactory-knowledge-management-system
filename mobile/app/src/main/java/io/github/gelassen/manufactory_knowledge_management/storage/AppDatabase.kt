package io.github.gelassen.manufactory_knowledge_management.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import io.github.gelassen.manufactory_knowledge_management.App

@Database(
    entities = [
/*        ChainTransactionEntity::class,
        ServerRequestTransactionEntity::class,
        MatchEntity::class,
        ChainServiceEntity::class*/
               ],
//    views = [DataItemFromView::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, App.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}