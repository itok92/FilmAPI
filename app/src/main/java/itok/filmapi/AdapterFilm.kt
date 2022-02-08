package itok.filmapi


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import itok.filmapi.databinding.ItemAdapterFilmBinding

import itok.filmapi.network.response.ResponseUpcoming
import itok.filmapi.network.response.ResultsItem


class AdapterFilm(private val onClick: (ResultsItem?) -> Unit) :
    RecyclerView.Adapter<AdapterFilm.ViewHolder>() {

    private var dataFilm = mutableListOf<ResultsItem?>()

    fun addArtikel(list: List<ResultsItem?>, clearAll: Boolean = false) {
        if (clearAll)
            dataFilm = mutableListOf()

        dataFilm.addAll(list)
        notifyItemInserted(dataFilm.size)
    }

    inner class ViewHolder(private val binding: ItemAdapterFilmBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBindingItem(responseUpcoming: ResultsItem?) {
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





