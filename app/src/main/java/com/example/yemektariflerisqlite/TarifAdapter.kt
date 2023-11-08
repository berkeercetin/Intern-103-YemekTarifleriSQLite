import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yemektariflerisqlite.R
import com.example.yemektariflerisqlite.Tarif

class TarifAdapter(private val tarifListesi: List<Tarif>) : RecyclerView.Adapter<TarifAdapter.TarifViewHolder>() {

    inner class TarifViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val baslikTextView: TextView = itemView.findViewById(R.id.textViewBaslik2)
        val metinTextView: TextView = itemView.findViewById(R.id.textViewMetin2)
        val resimImageView: ImageView = itemView.findViewById(R.id.imageViewTarif)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarifViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tarif_item, parent, false)
        return TarifViewHolder(view)
    }

    override fun onBindViewHolder(holder: TarifViewHolder, position: Int) {
        val tarif = tarifListesi[position]

        // Verileri ViewHolder bileşenlerine aktar
        holder.baslikTextView.text = tarif.baslik
        holder.metinTextView.text = tarif.metin
        holder.resimImageView.setImageBitmap(tarif.bitmap)
    }

    override fun getItemCount(): Int {
        return tarifListesi.size
    }
}
