package ru.sibur.imgurbrowser.data.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import ru.sibur.imgurbrowser.data.model.ImagesResponse

interface ImageApi {

    @GET("image/{hash}/")
    fun getImage(@Path("hash") hash: String): Call<ImagesResponse>
}
