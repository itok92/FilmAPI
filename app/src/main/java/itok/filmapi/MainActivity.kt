package itok.filmapi

import android.app.ProgressDialog
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import itok.filmapi.databinding.ActivityMainBinding
import itok.filmapi.network.NetworkConfig
import itok.filmapi.network.response.ResponseFilm
import itok.filmapi.network.response.ResponseTopRated
import itok.filmapi.network.response.ResponseUpcoming
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapterFilm: AdapterFilm
    private lateinit var pd: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapterFilm = AdapterFilm{
//            val builder = CustomTabsIntent.Builder()
//            val customTabsIntent = builder.build()
//            customTabsIntent.launchUrl(this, Uri.parse(it?.))
        }
        binding.rvFilm.apply {
            adapter = adapterFilm
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        getFilm("us","8763016359f81d13701eea0948e1ef2a")
    }

    private fun getFilm(id: String, apiKey: String) {
        pd = ProgressDialog.show(this, "", "memuat artikel", true, false)
        NetworkConfig.getService().getHeadline(apiKey)
            .enqueue(object : retrofit2.Callback<ResponseUpcoming> {
                override fun onResponse(
                    call: Call<ResponseUpcoming>,
                    response: Response<ResponseUpcoming>
                ) {
                    pd.dismiss()
                    if (response.isSuccessful) {
                        val dataFilm = response.body()?.results
                        if (dataFilm != null) {
                            adapterFilm.addArtikel(dataFilm)
                        }
                    } else {
                        Toast.makeText(this@MainActivity, "Tidak ada data", Toast.LENGTH_SHORT)
                            .show()
                    }
                }


                override fun onFailure(call: Call<ResponseUpcoming>, t: Throwable) {
                    pd.dismiss()
                    Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
                }
            })
    }

}