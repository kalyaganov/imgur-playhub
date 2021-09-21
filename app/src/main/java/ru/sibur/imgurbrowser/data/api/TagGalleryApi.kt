package ru.sibur.imgurbrowser.data.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import ru.sibur.imgurbrowser.data.model.TagGalleryResponse

interface TagGalleryApi {

    @GET("gallery/t/{tag}/{sort}/{window}/{page}")
    fun getItems(
        @Path("tag") tag: String,
        @Path("sort") sort: String,
        @Path("window") window: String,
        @Path("page") page: Int
    ): Call<TagGalleryResponse>
}
