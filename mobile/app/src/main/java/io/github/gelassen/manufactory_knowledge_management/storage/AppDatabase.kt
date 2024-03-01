package io.github.gelassen.manufactory_knowledge_management.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import io.github.gelassen.manufactory_knowledge_management.App
import io.github.gelassen.manufactory_knowledge_management.storage.model.BreakdownAndPhotos
import io.github.gelassen.manufactory_knowledge_management.storage.model.BreakdownEntity
import io.github.gelassen.manufactory_knowledge_management.storage.model.MachineAndBreakdowns
import io.github.gelassen.manufactory_knowledge_management.storage.model.MachineEntity
import io.github.gelassen.manufactory_knowledge_management.storage.model.PhotofixationEntity

@Database(
    entities = [
        MachineEntity::class,
        BreakdownEntity::class,
        PhotofixationEntity::class,
/*        MachineAndBreakdowns::class,
        BreakdownAndPhotos::class*/
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