package com.example.uppstyle

import android.app.Notification.BigPictureStyle
import java.io.Serializable

data class FashionItem(
    val title: String,
    val brand: String,
    val price: Float,
    var size: String,
    val imageResId: Int,
    val tags: List<String>,
    var quantity: Int = 1,
    var isOnCart: Boolean = false,
) : Serializable