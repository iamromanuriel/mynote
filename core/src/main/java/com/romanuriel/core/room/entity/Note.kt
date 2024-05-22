package com.romanuriel.core.room.entity


import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.romanuriel.core.converter.ConverterDate
import java.util.Date
@Entity(tableName = "NOTE", foreignKeys = [
    ForeignKey(
        entity = Category::class,
        parentColumns = ["id"],
        childColumns = ["categoryId"]
    )
])
@TypeConverters(ConverterDate::class)
data class Note (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var categoryId: Long = 0,
    var title: String? = "",
    var dateCreate: Date? = null,
    var lastUpdate: Date? = null,
    var dateTime: Date? = null,
    var content: String? = null,
    var audioFilePath: String? = null,
    var idUser: Long? = null,
    var pin: Boolean? = false
)