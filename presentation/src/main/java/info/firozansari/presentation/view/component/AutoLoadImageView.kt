package info.firozansari.presentation.view.component

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.widget.ImageView
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL

/**
 * Simple implementation of [android.widget.ImageView] with extended features like setting an
 * image from an url and an internal file cache using the application cache directory.
 */
class AutoLoadImageView : ImageView {
    private var imageUrl: String? = null
    private var imagePlaceHolderResId = -1
    private val cache: DiskCache? = DiskCache(context.cacheDir)

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
    }

    override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()
        val savedState = SavedState(superState)
        savedState.imagePlaceHolderResId = imagePlaceHolderResId
        savedState.imageUrl = imageUrl
        return savedState
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state !is SavedState) {
            super.onRestoreInstanceState(state)
            return
        }
        val savedState = state as SavedState?
        super.onRestoreInstanceState(savedState.getSuperState())
        imagePlaceHolderResId = savedState.imagePlaceHolderResId
        imageUrl = savedState.imageUrl
        setImageUrl(imageUrl)
    }

    /**
     * Set an image from a remote url.
     *
     * @param imageUrl The url of the resource to load.
     */
    fun setImageUrl(imageUrl: String?) {
        this.imageUrl = imageUrl
        loadImagePlaceHolder()
        if (this.imageUrl != null) {
            loadImageFromUrl(this.imageUrl)
        } else {
            loadImagePlaceHolder()
        }
    }

    /**
     * Loads and image from the internet (and cache it) or from the internal cache.
     *
     * @param imageUrl The remote image url to load.
     */
    private fun loadImageFromUrl(imageUrl: String?) {
        object : Thread() {
            override fun run() {
                val bitmap = getFromCache(getFileNameFromUrl(imageUrl))
                if (bitmap != null) {
                    loadBitmap(bitmap)
                } else {
                    if (isThereInternetConnection()) {
                        val imageDownloader = ImageDownloader()
                        imageDownloader.download(imageUrl, object : ImageDownloader.Callback {
                            override fun onImageDownloaded(bitmap: Bitmap?) {
                                cacheBitmap(bitmap, getFileNameFromUrl(imageUrl))
                                loadBitmap(bitmap)
                            }

                            override fun onError() {
                                loadImagePlaceHolder()
                            }
                        })
                    } else {
                        loadImagePlaceHolder()
                    }
                }
            }
        }.start()
    }

    /**
     * Run the operation of loading a bitmap on the UI thread.
     *
     * @param bitmap The image to load.
     */
    private fun loadBitmap(bitmap: Bitmap?) {
        (context as Activity).runOnUiThread { setImageBitmap(bitmap) }
    }

    /**
     * Loads the image place holder if any has been assigned.
     */
    private fun loadImagePlaceHolder() {
        if (imagePlaceHolderResId != -1) {
            (context as Activity).runOnUiThread {
                setImageResource(
                    imagePlaceHolderResId
                )
            }
        }
    }

    /**
     * Get a [android.graphics.Bitmap] from the internal cache or null if it does not exist.
     *
     * @param fileName The name of the file to look for in the cache.
     * @return A valid cached bitmap, otherwise null.
     */
    private fun getFromCache(fileName: String?): Bitmap? {
        var bitmap: Bitmap? = null
        if (cache != null) {
            bitmap = cache[fileName]
        }
        return bitmap
    }

    /**
     * Cache an image using the internal cache.
     *
     * @param bitmap The bitmap to cache.
     * @param fileName The file name used for caching the bitmap.
     */
    private fun cacheBitmap(bitmap: Bitmap?, fileName: String?) {
        if (cache != null) {
            cache.put(bitmap, fileName)
        }
    }

    /**
     * Checks if the device has any active internet connection.
     *
     * @return true device with internet connection, otherwise false.
     */
    private fun isThereInternetConnection(): Boolean {
        val isConnected: Boolean
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting
        return isConnected
    }

    /**
     * Creates a file name from an image url
     *
     * @param imageUrl The image url used to build the file name.
     * @return An String representing a unique file name.
     */
    private fun getFileNameFromUrl(imageUrl: String?): String? {
        //we could generate an unique MD5/SHA-1 here
        var hash = imageUrl.hashCode().toString()
        if (hash.startsWith("-")) {
            hash = hash.substring(1)
        }
        return BASE_IMAGE_NAME_CACHED + hash
    }

    /**
     * Class used to download images from the internet
     */
    private class ImageDownloader internal constructor() {
        internal interface Callback {
            open fun onImageDownloaded(bitmap: Bitmap?)
            open fun onError()
        }

        /**
         * Download an image from an url.
         *
         * @param imageUrl The url of the image to download.
         * @param callback A callback used to be reported when the task is finished.
         */
        fun download(imageUrl: String?, callback: Callback?) {
            try {
                val conn = URL(imageUrl).openConnection()
                conn.connect()
                val bitmap = BitmapFactory.decodeStream(conn.getInputStream())
                callback?.onImageDownloaded(bitmap)
            } catch (e: IOException) {
                reportError(callback)
            }
        }

        /**
         * Report an error to the caller
         *
         * @param callback Caller implementing [Callback]
         */
        private fun reportError(callback: Callback?) {
            callback?.onError()
        }
    }

    /**
     * A simple disk cache implementation
     */
    private class DiskCache internal constructor(private val cacheDir: File?) {
        /**
         * Get an element from the cache.
         *
         * @param fileName The name of the file to look for.
         * @return A valid element, otherwise false.
         */
        @Synchronized
        operator fun get(fileName: String?): Bitmap? {
            var bitmap: Bitmap? = null
            val file = buildFileFromFilename(fileName)
            if (file.exists()) {
                bitmap = BitmapFactory.decodeFile(file.getPath())
            }
            return bitmap
        }

        /**
         * Cache an element.
         *
         * @param bitmap The bitmap to be put in the cache.
         * @param fileName A string representing the name of the file to be cached.
         */
        @Synchronized
        fun put(bitmap: Bitmap?, fileName: String?) {
            val file = buildFileFromFilename(fileName)
            if (!file.exists()) {
                try {
                    val fileOutputStream = FileOutputStream(file)
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream)
                    fileOutputStream.flush()
                    fileOutputStream.close()
                } catch (e: IOException) {
                    Log.e(TAG, e.message)
                }
            }
        }

        /**
         * Creates a file name from an image url
         *
         * @param fileName The image url used to build the file name.
         * @return A [java.io.File] representing a unique element.
         */
        private fun buildFileFromFilename(fileName: String?): File? {
            val fullPath = cacheDir.getPath() + File.separator + fileName
            return File(fullPath)
        }

        companion object {
            private val TAG: String? = "DiskCache"
        }
    }

    private class SavedState : BaseSavedState {
        var imagePlaceHolderResId = 0
        var imageUrl: String? = null

        internal constructor(superState: Parcelable?) : super(superState) {}
        private constructor(`in`: Parcel?) : super(`in`) {
            imagePlaceHolderResId = `in`.readInt()
            imageUrl = `in`.readString()
        }

        override fun writeToParcel(out: Parcel?, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeInt(imagePlaceHolderResId)
            out.writeString(imageUrl)
        }

        companion object {
            val CREATOR: Parcelable.Creator<SavedState?>? =
                object : Parcelable.Creator<SavedState?> {
                    override fun createFromParcel(`in`: Parcel?): SavedState? {
                        return SavedState(`in`)
                    }

                    override fun newArray(size: Int): Array<SavedState?>? {
                        return arrayOfNulls<SavedState?>(size)
                    }
                }
        }
    }

    companion object {
        private val BASE_IMAGE_NAME_CACHED: String? = "image_"
    }
}