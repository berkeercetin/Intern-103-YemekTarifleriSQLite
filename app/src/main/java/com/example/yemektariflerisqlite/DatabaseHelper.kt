package com.example.yemektariflerisqlite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.BitmapFactory
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "yemekler.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE IF NOT EXISTS yemekler (
                _id INTEGER PRIMARY KEY AUTOINCREMENT,
                baslik TEXT,
                metin TEXT,
                resim BLOB
            );
        """
        try {
            db.execSQL(createTableQuery)
        } catch (e: Exception) {
            Log.e("Database", "Yemek kaydedilirken bir hata oluştu: ${e.message}")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS yemekler")
            onCreate(db)
        }
    }

    fun insertTarif(baslik: String, metin: String, resim: ByteArray): Long {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put("baslik", baslik)
            put("metin", metin)
            put("resim", resim)
        }
        return db.insert("yemekler", null, contentValues)
    }

    @SuppressLint("Range")
    fun getTarifList(): List<Tarif> {
        val tarifList = mutableListOf<Tarif>()
        val db = readableDatabase
        val query = "SELECT * FROM yemekler"
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("_id"))
                val baslik = cursor.getString(cursor.getColumnIndex("baslik"))
                val metin = cursor.getString(cursor.getColumnIndex("metin"))
                val resimByteArray = cursor.getBlob(cursor.getColumnIndex("resim"))
                val bitmap = BitmapFactory.decodeByteArray(resimByteArray, 0, resimByteArray.size)

                val tarif = Tarif(id, baslik, metin, bitmap)
                tarifList.add(tarif)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return tarifList
    }
}
