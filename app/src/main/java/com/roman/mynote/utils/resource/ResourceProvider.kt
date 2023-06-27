package com.roman.mynote.utils.resource

import android.content.Context

class ResourceProvider(
    private val context: Context
): IResourceProvider {

    override fun getStringResource(id: Int): String {
        return context.getString(id)
    }
}