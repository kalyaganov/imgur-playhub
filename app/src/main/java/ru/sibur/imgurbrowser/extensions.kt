package ru.sibur.imgurbrowser

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.floor

fun ViewGroup.layoutInflater(): LayoutInflater = LayoutInflater.from(context)

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

val Float.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

/**
 * Высчитываем число элементов в одном ряду динамически в зависимости от размера контейнера
 */
fun RecyclerView.applyAutoColumnGridLayoutManager(
    adapter: RecyclerView.Adapter<*>,
    maxItemWidth: Int,
    startEndMargin: Int
) {
    this.doOnPreDraw {
        val spanCount =
            floor((it.measuredWidth - 2 * startEndMargin) / maxItemWidth.toDouble()).toInt()
        this.adapter = adapter
        this.layoutManager = GridLayoutManager(it.context, spanCount)
    }
}

fun Fragment.getDrawableCompat(@DrawableRes id: Int): Drawable? =
    ResourcesCompat.getDrawable(resources, id, requireContext().theme)

var View.isVisible: Boolean
    get() = this.visibility == View.VISIBLE
    set(value) {
        this.visibility = if (value) View.VISIBLE else View.GONE
    }

fun Context.isLandscape(): Boolean =
    resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

inline fun Fragment.launchAndRepeatWithViewLifecycle(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend CoroutineScope.() -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(minActiveState) {
            block()
        }
    }
}
