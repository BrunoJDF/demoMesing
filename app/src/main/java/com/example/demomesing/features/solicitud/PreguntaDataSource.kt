package com.example.demomesing.features.solicitud

import com.example.demomesing.data.ObjectOperation

interface PreguntaDataSource {
    fun createSolicitude(
        p1: Int,
        p2: Int,
        p3: Int,
        cel: String,
        email: String,
        id: Int,
        idOfer: Int,
        objectOperation: ObjectOperation
    )
    fun getPreguntas(objectOperation: ObjectOperation)
}