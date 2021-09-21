package ru.sibur.imgurbrowser.data.api

import retrofit2.Call
import retrofit2.http.GET
import ru.sibur.imgurbrowser.data.model.TagsResponse

interface TagsApi {

    @GET("tags")
    fun getTags(): Call<TagsResponse>
}
