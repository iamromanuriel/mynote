package com.romanuriel.domain.model

import com.romanuriel.utils.TypeCategory

data class NoteDetail (
    var id: Long,
    var title: String? = "",
    var category: TypeCategory ?= null,
    var create: String? = "",
    var lastUpdate: String? = "",
    var lackOfTime: String? = "",
    var content: String? = null,
    var filePath: String? = null,
    var pin: Boolean? = false
)