package com.minapp.android.test.util

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.request.RequestOptions
import com.minapp.android.test.R
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.ImageEngine
import com.zhihu.matisse.filter.Filter
import com.zhihu.matisse.internal.entity.IncapableCause
import com.zhihu.matisse.internal.entity.Item
import java.text.NumberFormat


/**
 * Created by linwei on 2017/12/12.
 */

object MatisseHelper {

    /**
     * 使用 Matisse 构建一个通用的图片选择器
     * @param maxSelectable
     * @param requestCode
     * @param activity
     */
    fun createImageSelector(requestCode: Int, fragment: Fragment) {
        Matisse.from(fragment)
            .choose(MimeType.of(MimeType.PNG, MimeType.JPEG, MimeType.BMP))
            .theme(R.style.Matisse_Zhihu)
            .maxSelectable(1)
            .countable(true)
            .imageEngine(Glide4Engine())
            .addFilter(DefaultFilter()).showSingleMediaType(true)
            .forResult(requestCode)
    }
}

private class DefaultFilter : Filter() {

    override fun filter(context: Context, item: Item): IncapableCause? = null

    override fun constraintTypes(): MutableSet<MimeType>? = null

}

/**
 * {@link ImageEngine} implementation using Glide.
 * copy from https://github.com/zhihu/Matisse/blob/master/sample/src/main/java/com/zhihu/matisse/sample/Glide4Engine.java
 */
class Glide4Engine : ImageEngine {

    override fun loadThumbnail(context: Context, resize: Int, placeholder: Drawable, imageView: ImageView, uri: Uri) {
        Glide.with(context)
                .asBitmap() // some .jpeg files are actually gif
                .load(uri)
                .apply(RequestOptions()
                        .override(resize, resize)
                        .placeholder(placeholder)
                        .centerCrop())
                .into(imageView)
    }

    override fun loadGifThumbnail(context: Context, resize: Int, placeholder: Drawable, imageView: ImageView,
                                  uri: Uri) {
        Glide.with(context)
                .asBitmap() // some .jpeg files are actually gif
                .load(uri)
                .apply(RequestOptions()
                        .override(resize, resize)
                        .placeholder(placeholder)
                        .centerCrop())
                .into(imageView)
    }

    override fun loadImage(context: Context, resizeX: Int, resizeY: Int, imageView: ImageView, uri: Uri) {
        Glide.with(context)
                .load(uri)
                .apply(RequestOptions()
                        .override(resizeX, resizeY)
                        .priority(Priority.HIGH)
                        .fitCenter())
                .into(imageView)
    }

    override fun loadGifImage(context: Context, resizeX: Int, resizeY: Int, imageView: ImageView, uri: Uri) {
        Glide.with(context)
                .asGif()
                .load(uri)
                .apply(RequestOptions()
                        .override(resizeX, resizeY)
                        .priority(Priority.HIGH)
                        .fitCenter())
                .into(imageView)
    }

    override fun supportAnimatedGif(): Boolean {
        return true
    }

}