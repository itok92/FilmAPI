package itok.filmapi.network

import itok.filmapi.network.response.ResponseFilm
import itok.filmapi.network.response.ResponseNowPlaying
import itok.filmapi.network.response.ResponseTopRated
import itok.filmapi.network.response.ResponseUpcoming
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("3/movie/upcoming")
    fun getHeadline(
        @Query("api_key") apiKey: String?

    ): retrofit2.Call<ResponseUpcoming>

    @GET("3/movie/top_rated")
    fun getTopRated(
        @Query("api_key") apiKey: String?

    ): retrofit2.Call<ResponseTopRated>

    @GET("3/movie/now_playing")
    fun getNowPlaying(
        @Query("api_key") apiKey: String?

    ): retrofit2.Call<ResponseNowPlaying>
}

