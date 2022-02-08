package itok.filmapi

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import itok.filmapi.databinding.ActivityNowPlayingBinding
import itok.filmapi.network.NetworkConfig
import itok.filmapi.network.response.ResponseNowPlaying
import retrofit2.Call
import retrofit2.Response

class NowPlayingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNowPlayingBinding
    private lateinit var adapterFilm: AdapterNowPlaying
    private lateinit var pd: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNowPlayingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapterFilm = AdapterNowPlaying {

        }
        binding.nowPlaying.apply {
            adapter = adapterFilm
            layoutManager = LinearLayoutManager(this@NowPlayingActivity)
        }

        getNowPlaying("us", "8763016359f81d13701eea0948e1ef2a")
    }
    private fun getNowPlaying(id: String, apiKey: String) {
        pd = ProgressDialog.show(this, "", "memuat artikel", true, false)
        NetworkConfig.getService().getNowPlaying( apiKey)
            .enqueue(object : retrofit2.Callback<ResponseNowPlaying> {
                override fun onResponse(
                    call: Call<ResponseNowPlaying>,
                    response: Response<ResponseNowPlaying>
                ) {
                    pd.dismiss()
                    if (response.isSuccessful) {
                        val dataFilm = response.body()?.results
                        if (dataFilm != null) {
                            adapterFilm.addArtikel(dataFilm)
                        }
                    } else {
                        Toast.makeText(this@NowPlayingActivity, "Tidak ada data", Toast.LENGTH_SHORT)
                            .show()
                    }
                }


                override fun onFailure(call: Call<ResponseNowPlaying>, t: Throwable) {
                    pd.dismiss()
                    Toast.makeText(this@NowPlayingActivity, t.message, Toast.LENGTH_SHORT).show()
                }


            })
    }
}