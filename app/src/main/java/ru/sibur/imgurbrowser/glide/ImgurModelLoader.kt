package ru.sibur.imgurbrowser.glide

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.*
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader
import kotlinx.coroutines.runBlocking
import ru.sibur.imgurbrowser.domain.ImageInteractor
import ru.sibur.imgurbrowser.model.ImgurImageHash
import java.io.InputStream

class ImgurModelLoader private constructor(
    private val imageInteractor: ImageInteractor,
    modelLoader: ModelLoader<GlideUrl, InputStream>,
    modelHash: ModelCache<ImgurImageHash, GlideUrl>
) : BaseGlideUrlLoader<ImgurImageHash>(modelLoader, modelHash) {

    class Factory(private val imageInteractor: ImageInteractor) :
        ModelLoaderFactory<ImgurImageHash, InputStream> {
        private val modelCache: ModelCache<ImgurImageHash, GlideUrl> =
            ModelCache<ImgurImageHash, GlideUrl>(500)

        override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<ImgurImageHash, InputStream> {
            return ImgurModelLoader(
                imageInteractor,
                multiFactory.build(GlideUrl::class.java, InputStream::class.java),
                modelCache
            )
        }

        override fun teardown() {}
    }

    private val urlCache: HashMap<String, String> = HashMap()

    override fun handles(model: ImgurImageHash): Boolean = true

    override fun getUrl(model: ImgurImageHash, width: Int, height: Int, options: Options?): String {
        return urlCache.getOrElse(model.hash) {
            val link = runBlocking { imageInteractor.getImage(model.hash).link }
            urlCache[model.hash] = link
            link
        }
    }
}
