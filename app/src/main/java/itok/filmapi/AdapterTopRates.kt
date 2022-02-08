package itok.filmapi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import itok.filmapi.databinding.ItemAdapterFilmBinding
import itok.filmapi.network.response.ResultsItem
import itok.filmapi.network.response.ResultsTopRates

class AdapterTopRates (private val onClick: (ResultsTopRates?) -> Unit) :
    RecyclerView.Adapter<AdapterTopRates.ViewHolder>() {

    private var dataFilm = mutableListOf<ResultsTopRates?>()

    fun addArtikel(list: List<ResultsTopRates?>, clearAll: Boolean = false) {
        if (clearAll)
            dataFilm = mutableListOf()

        dataFilm.addAll(list)
        notifyItemInserted(dataFilm.size)
    }

    inner class ViewHolder(private val binding: ItemAdapterFilmBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBindingItem(responseUpcoming: ResultsTopRates?) {
            binding.judul.text = responseUpcoming?.title
            binding.postOn.text = responseUpcoming?.releaseDate
            Glide.with(binding.root.context)
                .load("https://image.tmdb.org/t/p/original"+responseUpcoming?.posterPath)
                .into(binding.cover)

            binding.root.setOnClickListener {
                onClick(responseUpcoming)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemAdapterFilmBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return dataFilm.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindingItem(dataFilm[position])
    }
}