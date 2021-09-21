package ru.sibur.imgurbrowser.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.sibur.imgurbrowser.domain.ImageInteractor
import ru.sibur.imgurbrowser.model.ImgurImageHash
import java.io.InputStream

@GlideModule
class GlideConfigurator : AppGlideModule() {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface GlideConfiguratorDependencies {
        val imageInteractor: ImageInteractor
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        val deps =
            EntryPoints.get(context.applicationContext, GlideConfiguratorDependencies::class.java)

        registry.append(
            ImgurImageHash::class.java,
            InputStream::class.java,
            ImgurModelLoader.Factory(deps.imageInteractor)
        )
    }

    override fun isManifestParsingEnabled(): Boolean = false
}
