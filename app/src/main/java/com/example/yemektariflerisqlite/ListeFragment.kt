package com.example.yemektariflerisqlite

import TarifAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Constraint
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)!!
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val dbHelper = DatabaseHelper(requireContext())
        val tarifListesi = dbHelper.getTarifList()
        if (tarifListesi.isNotEmpty()) {
            // Veriler mevcut, RecyclerView.Adapter'e aktarın
            val adapter = TarifAdapter(tarifListesi)
            recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()

        }
        Log.e("TAG",tarifListesi.size.toString())
    }
}