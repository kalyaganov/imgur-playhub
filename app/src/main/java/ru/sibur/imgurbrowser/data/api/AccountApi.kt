package ru.sibur.imgurbrowser.data.api

import retrofit2.Call
import retrofit2.http.GET
import ru.sibur.imgurbrowser.data.model.AccountImagesResponse

interface AccountApi {

    @GET("account/me/images")
    fun getMyImages(): Call<AccountImagesResponse>
}
