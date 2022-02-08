package itok.filmapi

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import itok.filmapi.databinding.ActivityMainBinding
import itok.filmapi.databinding.ActivityTopRatesMovieBinding
import itok.filmapi.databinding.ItemAdapterFilmBinding
import itok.filmapi.network.NetworkConfig
import itok.filmapi.network.response.ResponseTopRated
import itok.filmapi.network.response.ResultsItem
import retrofit2.Call
import retrofit2.Response

class TopRatesMovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTopRatesMovieBinding
    private lateinit var adapterFilm: AdapterTopRates
    private lateinit var pd: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopRatesMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapterFilm = AdapterTopRates {
//            val builder = CustomTabsIntent.Builder()
//            val customTabsIntent = builder.build()
//            customTabsIntent.launchUrl(this, Uri.parse(it?.))
        }
        binding.topRated.apply {
            adapter = adapterFilm
            layoutManager = LinearLayoutManager(this@TopRatesMovieActivity)
        }

        getTopRates("us", "8763016359f81d13701eea0948e1ef2a")
    }
    private fun getTopRates(id: String, apiKey: String) {
        pd = ProgressDialog.show(this, "", "memuat artikel", true, false)
        NetworkConfig.getService().getTopRated( apiKey)
            .enqueue(object : retrofit2.Callback<ResponseTopRated> {
                override fun onResponse(
                    call: Call<ResponseTopRated>,
                    response: Response<ResponseTopRated>
                ) {
                    pd.dismiss()
                    if (response.isSuccessful) {
                        val dataFilm = response.body()?.results
                        if (dataFilm != null) {
                            adapterFilm.addArtikel(dataFilm)
                        }
                    } else {
                        Toast.makeText(this@TopRatesMovieActivity, "Tidak ada data", Toast.LENGTH_SHORT)
                            .show()
                    }
                }


                override fun onFailure(call: Call<ResponseTopRated>, t: Throwable) {
                    pd.dismiss()
                    Toast.makeText(this@TopRatesMovieActivity, t.message, Toast.LENGTH_SHORT).show()
                }


            })
    }
}