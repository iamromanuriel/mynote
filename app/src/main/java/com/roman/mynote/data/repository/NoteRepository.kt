package com.roman.mynote.data.repository

import com.roman.mynote.utils.resource.IResourceProvider
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val resourceProvider: IResourceProvider
    ) {


    suspend fun getListNames(): List<String>{
        return listOf("Rojo","Amarillo","Azul","Blanco","Verde","Colorado")
    }

    suspend fun getDaysWeek(): List<String>{
        return listOf("Lunes","Martes","Miercoles","Jueves","Viernes")
    }

}
