package com.mobile.core.utils

import android.util.TypedValue
import android.view.View

class TypedValueUtils {
    companion object {
        fun resolveThemeColor(view: View, resourceId: Int): Int = TypedValue().apply {
            view.context.theme.resolveAttribute(resourceId, this, true)
        }.data
    }
}