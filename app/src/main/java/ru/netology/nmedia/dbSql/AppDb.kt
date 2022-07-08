package ru.netology.nmedia.dbSql

import android.content.Context
import android.database.sqlite.SQLiteDatabase



class AppDb private constructor(db: SQLiteDatabase) {
    val postDao: PostDao = PostDaoImpl(db)

    companion object {
        @Volatile
        private var instance: AppDb? = null

        fun getInstance(context: Context): AppDb {
            return instance ?: synchronized(this) {
                instance ?: AppDb(
                    buildDatabase(context)
                ).also { instance =it }
            }
        }

        private fun buildDatabase(context: Context) = DbHelper(
            context, 1, "app.db",
        ).writableDatabase
    }
}

