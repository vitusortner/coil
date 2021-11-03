@file:JvmName("-SingletonExtensions")
@file:Suppress("NOTHING_TO_INLINE", "unused")

package coil

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import coil.request.Disposable
import coil.request.ImageRequest
import coil.request.ImageResult
import coil.util.CoilUtils
import okhttp3.HttpUrl
import java.io.File
import java.nio.ByteBuffer

/**
 * Get the singleton [ImageLoader].
 */
inline val Context.imageLoader: ImageLoader
    get() = Coil.imageLoader(this)

/**
 * Load the image referenced by [data] and set it on this [ImageView].
 *
 * Example:
 * ```
 * imageView.load("https://www.example.com/image.jpg") {
 *     crossfade(true)
 *     transformations(CircleCropTransformation())
 * }
 * ```
 *
 * The default supported [data] types  are:
 *
 * - [String] (treated as a [Uri])
 * - [Uri] (`android.resource`, `content`, `file`, `http`, and `https` schemes)
 * - [HttpUrl]
 * - [File]
 * - [DrawableRes] [Int]
 * - [Drawable]
 * - [Bitmap]
 * - [ByteBuffer]
 *
 * @param data The data to load.
 * @param imageLoader The [ImageLoader] that will be used to enqueue the [ImageRequest].
 *  By default, the singleton [ImageLoader] will be used.
 * @param builder An optional lambda to configure the [ImageRequest].
 */
inline fun ImageView.load(
    data: Any?,
    imageLoader: ImageLoader = context.imageLoader,
    builder: ImageRequest.Builder.() -> Unit = {}
): Disposable {
    val request = ImageRequest.Builder(context)
        .apply(builder)
        .data(data)
        .target(this)
        .build()
    return imageLoader.enqueue(request)
}

/**
 * Dispose the request attached to this view (if there is one).
 */
inline fun ImageView.dispose() {
    CoilUtils.dispose(this)
}

/**
 * Get the [ImageResult] of the most recent executed image request attached to this view.
 */
inline val ImageView.result: ImageResult?
    get() = CoilUtils.result(this)

@Deprecated(
    message = "Migrate to 'load'.",
    replaceWith = ReplaceWith(
        expression = "load(data, imageLoader, builder)",
        imports = ["coil.imageLoader", "coil.load"]
    ),
    level = DeprecationLevel.ERROR // Temporary migration aid.
)
inline fun ImageView.loadAny(
    data: Any?,
    imageLoader: ImageLoader = context.imageLoader,
    builder: ImageRequest.Builder.() -> Unit = {}
) = load(data, imageLoader, builder)

@Deprecated(
    message = "Migrate to 'dispose'.",
    replaceWith = ReplaceWith(
        expression = "dispose()",
        imports = ["coil.dispose"]
    ),
    level = DeprecationLevel.ERROR // Temporary migration aid.
)
inline fun ImageView.clear() = dispose()
