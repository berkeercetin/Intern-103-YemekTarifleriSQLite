package com.example.yemektariflerisqlite

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatImageView
import com.example.yemektariflerisqlite.databinding.ActivityMainBinding
import java.io.ByteArrayOutputStream


class TarifFragment : Fragment() {
    private lateinit var selectedImage: AppCompatImageView
    private lateinit  var test : Uri

    fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tarif, container, false)
    }

    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button = view.findViewById<Button>(R.id.button)
        val isim = view.findViewById<EditText>(R.id.yemekİsmiText)
        val malzemeler = view.findViewById<EditText>(R.id.malzemelerText)
        selectedImage = view.findViewById(R.id.imageView2)
        button.setOnClickListener {
            saveSQL(isim.text.toString(),malzemeler.text.toString())
        }
        selectedImage.setOnClickListener {
            val pickImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            changeImage.launch(pickImg)
        }
    }

    fun isTableExists(db: SQLiteDatabase, tabloAdi: String): Boolean {
        val cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='$tabloAdi'", null)
        val tabloVar = cursor.count > 0
        cursor.close()
        return tabloVar
    }

    fun saveSQL(baslik: String, metin: String) {
        val dbHelper = DatabaseHelper(requireContext())
        if (this.test == null) {
            Log.e("Database", "Resim seçilmedi.")
            return
        }
        val bitmap = BitmapFactory.decodeStream(requireContext().contentResolver.openInputStream(this.test))
        val imageByteArray = bitmapToByteArray(bitmap)

        try {
            dbHelper.insertTarif(baslik,metin,imageByteArray)
        } catch (e: Exception) {
            Log.e("Database", "Yemek kaydedilirken bir hata oluştu: ${e.message}")
        }

    }
    private val changeImage =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data
                this.test=data?.data!!
                val imgUri = data?.data
                selectedImage.setImageURI(imgUri)
            }
        }
}