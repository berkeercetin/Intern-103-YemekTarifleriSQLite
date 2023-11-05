package com.example.yemektariflerisqlite

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ListeFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_liste, container, false)
    }

    @SuppressLint("Range")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val database = requireContext().openOrCreateDatabase("yemekler.db", Context.MODE_PRIVATE, null)
        val tarifListesi = mutableListOf<Any>()
        val sorgu = "SELECT * FROM yemekler"
        val cursor = database.rawQuery(sorgu, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("_id"))
                val baslik = cursor.getString(cursor.getColumnIndex("baslik"))
                val metin = cursor.getString(cursor.getColumnIndex("metin"))
                val resimByteArray = cursor.getBlob(cursor.getColumnIndex("resim"))
                val bitmap = BitmapFactory.decodeByteArray(resimByteArray, 0, resimByteArray.size)

                val tarif = object {
                    val id = id
                    val baslik = baslik
                    val metin = metin
                    val bitmap = bitmap
                }
                tarifListesi.add(tarif)
            } while (cursor.moveToNext())
        }
        cursor.close()
        Log.e("TAG",tarifListesi.size.toString())
    }




}