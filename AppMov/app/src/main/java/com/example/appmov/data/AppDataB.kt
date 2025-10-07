package com.example.appmov.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Base de datos local con Room (concepto: persistencia de datos a largo plazo)

@Database(entities = [User::class], version = 1)
abstract class AppDataB : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile private var INSTANCE: AppDataB? = null

        fun getDatab(context: Context): AppDataB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataB::class.java,
                    "app_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}