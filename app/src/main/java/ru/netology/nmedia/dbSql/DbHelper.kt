package ru.netology.nmedia.dbSql

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream


class DbHelper(context: Context, dbVersion: Int, dbName: String) :
    SQLiteOpenHelper(context, dbName, null, dbVersion) {
      init {
        try {
            val file = context.getDatabasePath(dbName)
            if (!file.exists()) {
                val inputStream: InputStream = context.assets.open("posts.db")
                val os: OutputStream = FileOutputStream(file)
                val buffer = ByteArray(1024)
                while (inputStream.read(buffer) > 0) {
                    os.write(buffer)
                }
                os.flush()
                os.close()
                inputStream.close()
            }
        } catch (e: SQLiteException) {
            e.message
        }

    }

    override fun onCreate(db: SQLiteDatabase) {
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        TODO("Not implemented")
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        TODO("Not implemented")
    }
}